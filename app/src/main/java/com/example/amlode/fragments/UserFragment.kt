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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.squareup.picasso.Picasso

class UserFragment : Fragment() {
    private lateinit var viewFragment : View
    lateinit var username: TextView
    lateinit var email: TextView
    lateinit var points: TextView
    lateinit var photo: ImageView
    lateinit var date: TextView
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var logout_user: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFragment = inflater.inflate(R.layout.fragment_user, container, false)
        findById()
        return viewFragment
    }

    override fun onStart() {
        super.onStart()

        if(!prefs.getEmail().isEmpty()){
            showData()
        }else{
            val action = UserFragmentDirections.actionUserFragmentToDateFragment("actionLoginFragmentToUserFragment")
            viewFragment.findNavController().navigate(action)
        }
    }

    private fun showData(){
        username.text = "Nombre y apellido: " + prefs.getName() + " ${prefs.getLastName()}"
        email.text = "Email: " + prefs.getEmail()
        Log.d("PUNTOS SHOW DATA" ,"${prefs.getPoints()}")
        points.setText("${prefs.getPoints()}")
        date.setText("Fecha de nacimiento: " + prefs.getDate())
        Picasso.with(context).load(prefs.getPhoto()).into(photo)
        logOut()
    }

    private fun findById(){
        username = viewFragment.findViewById(R.id.username)
        email = viewFragment.findViewById(R.id.email)
        date = viewFragment.findViewById(R.id.date_user)
        points = viewFragment.findViewById(R.id.points)
        photo =  viewFragment.findViewById(R.id.photo)
        logout_user = viewFragment.findViewById(R.id.logout_user)
    }

    private fun logOut(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        logout_user.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener {
                val intent = Intent(context, MainActivity::class.java)
                Toast.makeText(context, "Logging Out", Toast.LENGTH_SHORT).show()
                prefs.setName("")
                prefs.setLastName("")
                prefs.setEmail("")
                prefs.saveDate("")
                startActivity(intent)
            }
        }
    }
}