package com.example.amlode.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.amlode.*
import com.example.amlode.MainActivity.Companion.prefs
import com.example.amlode.api.APIService
import com.example.amlode.data.DeaResponse
import com.example.amlode.data.UserResponse
import com.example.amlode.entities.DeaListado
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserFragment : Fragment() {
    private lateinit var viewFragment: View
    lateinit var username: TextView
    lateinit var email: TextView
    lateinit var points: TextView
    lateinit var photo: ImageView
    lateinit var date: TextView
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var logout_user: ImageButton
    lateinit var bot_deas_user: Button
    private var deas: ArrayList<DeaListado> = ArrayList()
    private val apiUser = APIService.createUserAPI()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFragment = inflater.inflate(R.layout.fragment_user, container, false)
        callUserByEmail()
        findById()
        return viewFragment
    }

    override fun onStart() {
        super.onStart()

        if (!prefs.getEmail().isEmpty()) {
            showData()
        } else {
            val action =
                UserFragmentDirections.actionUserFragmentToDateFragment("actionLoginFragmentToUserFragment")
            viewFragment.findNavController().navigate(action)
        }

        //IR AL LISTADO DE DEAS DEL USUARIO
        bot_deas_user.setOnClickListener {
            val action =
                UserFragmentDirections.actionUserFragmentToDeaListFragment(deas.toTypedArray())
            viewFragment.findNavController().navigate(action)
        }
    }

    private fun showData() {
        username.text = "Nombre y apellido: " + prefs.getName() + " ${prefs.getLastName()}"
        email.text = "Email: " + prefs.getEmail()
        points.setText("${prefs.getPoints()}")
        date.setText("Fecha de nacimiento: " + prefs.getDate())
        Picasso.with(context).load(prefs.getPhoto()).into(photo)
        logOut()
    }

    private fun findById() {
        username = viewFragment.findViewById(R.id.username)
        email = viewFragment.findViewById(R.id.email)
        date = viewFragment.findViewById(R.id.date_user)
        points = viewFragment.findViewById(R.id.points)
        photo = viewFragment.findViewById(R.id.photo)
        logout_user = viewFragment.findViewById(R.id.logout_user)
        bot_deas_user = viewFragment.findViewById(R.id.bot_deas_user)
    }

    private fun logOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        logout_user.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener {
                val intent = Intent(context, SplashActivity::class.java)
                Toast.makeText(context, "Logging Out", Toast.LENGTH_SHORT).show()
                prefs.setName("")
                prefs.setLastName("")
                prefs.setEmail("")
                prefs.saveDate("")
                startActivity(intent)
            }
        }
    }

    private fun callUserByEmail() {
        apiUser.getUser("v2/entities/${prefs.getEmail()}?type=user")
            ?.enqueue(object : Callback<UserResponse?> {
                override fun onResponse(call: Call<UserResponse?>, user: Response<UserResponse?>) {
                    val user: UserResponse? = (user.body())!!
                    if (user != null) {
                        prefs.savePoints(user.points.value.toInt())
                        addDeasList(user.deas.value)
                    }
                }

                override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                    Log.w("FAILURE", "Failure Call Get")
                }
            })
    }


    private fun addDeasList(deasUser: ArrayList<String>) {
        val apiDea = APIService.createDeaAPI()
        for (idDea in deasUser) {
            apiDea.getDea("v2/entities/${idDea}?type=dea")
                ?.enqueue(object : Callback<DeaResponse?> {
                    override fun onResponse(call: Call<DeaResponse?>, dea: Response<DeaResponse?>) {
                        val dea: DeaResponse? = (dea.body())!!
                        if (dea != null && dea.active.value) {
                                deas.add(DeaListado(" ${dea.address.value}", "${dea.id}"))
                        }
                    }

                    override fun onFailure(call: Call<DeaResponse?>, t: Throwable) {
                        Log.w("FAILURE", "Failure Call Get")
                    }
                })
        }
    }
}