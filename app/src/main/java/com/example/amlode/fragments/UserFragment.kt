package com.example.amlode.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
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
    lateinit var photo: ImageView
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var logout_user: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFragment = inflater.inflate(R.layout.fragment_user, container, false)
        username = viewFragment.findViewById(R.id.username)
        email = viewFragment.findViewById(R.id.email)
        photo =  viewFragment.findViewById(R.id.photo)
        logout_user = viewFragment.findViewById(R.id.logout_user)

        if(!prefs.getUsername().isEmpty()){
            showData()
        }else{
            val intent = Intent(context, LoginScreen::class.java)
            startActivity(intent)
        }
        return viewFragment
    }

    override fun onStart() {
        super.onStart()
    }

    private fun showData(){
        username.text = prefs.getUsername()
        email.text = prefs.getEmail()
        Picasso.with(context).load(prefs.getPhoto()).into(photo)
        logout_user.setVisibility(View.VISIBLE);

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        logout_user.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener {
                val intent = Intent(context, MainActivity::class.java)
                Toast.makeText(context, "Logging Out", Toast.LENGTH_SHORT).show()
                prefs.setUsername("")
                prefs.setEmail("")
                startActivity(intent)
            }
        }
    }

}