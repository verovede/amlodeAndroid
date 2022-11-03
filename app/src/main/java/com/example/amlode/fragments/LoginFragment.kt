package com.example.amlode.fragments

import android.os.Bundle
import android.view.View
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.amlode.MainActivity.Companion.prefs
import com.example.amlode.R
import com.example.amlode.api.APIService
import com.example.amlode.data.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.fragment_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val Req_Code: Int = 123
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var viewFragment : View
    private lateinit var fragment: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFragment = inflater.inflate(R.layout.fragment_login, container, false)
        return viewFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(requireContext())
    }

    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, Req_Code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                updateUI(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                prefs.setEmail(account.email.toString())
                prefs.setName(account.givenName.toString())
                prefs.setLastName(account.familyName.toString())
                prefs.savePhoto(account.photoUrl)
                postUser()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val frag = DateFragmentArgs.fromBundle(requireArguments()).fragName
        fragment = frag

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        firebaseAuth = FirebaseAuth.getInstance()

        Signin.setOnClickListener {
            Toast.makeText(context, "Logging In", Toast.LENGTH_SHORT).show()
            signInGoogle()
        }
    }

    private fun postUser(){
        val api = APIService.createUserAPI()
        val newUser = createUser()
        api.postUser(newUser)?.enqueue(
            object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.w("FAILURE", "Failure Call Post")
                }
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Log.w("SUCCESS", "SUCCESS Call Post")
                    callUserByEmail()
                }
            }
        )
    }

    private fun createUser(): UserResponse {
        val deas: ArrayList<String> = ArrayList()

        return UserResponse(
            "${prefs.getEmail()}",
            "user",
            BooleanValue("Boolean", true),
            ArrayValue("StructuredValue", deas),
            StringValue("String", "${prefs.getDate()}"),
            StringValue("String", "${prefs.getName()}"),
            StringValue("String", "${prefs.getLastName()}"),
            NumberValue("Number", 0)
        )
    }

    private fun callUserByEmail() {
        val apiUser = APIService.createUserAPI()
        apiUser.getUser("v2/entities/${prefs.getEmail()}?type=user")
            ?.enqueue(object : Callback<UserResponse?> {
                override fun onResponse(call: Call<UserResponse?>, user: Response<UserResponse?>) {
                    val user: UserResponse? = (user.body())!!
                    if (user != null) {
                      prefs.savePoints(user.points.value.toInt())
                    }
                    navigate()
                }
                override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                    Log.w("FAILURE", "Failure Call Post")
                }
            })
    }

    private fun navigate(){
        if (fragment == "actionLoginFragmentToUserFragment") {
            val action = LoginFragmentDirections.actionLoginFragmentToUserFragment()
            viewFragment.findNavController().navigate(action)
        }
        if (fragment == "actionLoginFragmentToMapFragment") {
            val action = LoginFragmentDirections.actionLoginFragmentToMapFragment()
            viewFragment.findNavController().navigate(action)
        }
    }
}

