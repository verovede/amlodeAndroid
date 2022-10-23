package com.example.amlode.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
    lateinit var points: TextView
    lateinit var photo: ImageView
    lateinit var date: TextView
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var logout_user: ImageButton
    lateinit var spinner: ProgressBar
    lateinit var icon: ImageView
    lateinit var title: TextView
    lateinit var totalPoints: TextView
    lateinit var button_deas: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFragment = inflater.inflate(R.layout.fragment_user, container, false)
        findById()

        if(!prefs.getUsername().isEmpty()){
            showData()
        }else{
            spinner.setVisibility(View.VISIBLE);
            val intent = Intent(context, DateFragment::class.java)
            startActivity(intent)
        }
        return viewFragment
    }

    override fun onStart() {
        super.onStart()
    }

    private fun showData(){
        visibility()
        username.text = prefs.getUsername()
        email.text = prefs.getEmail()
        points.setText("Puntos acumulados: ")
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
        icon = viewFragment.findViewById(R.id.icoAid)
        spinner = viewFragment.findViewById(R.id.spinner_user)
        title= viewFragment.findViewById(R.id.title_aid)
        totalPoints = viewFragment.findViewById(R.id.totalPuntos)
        button_deas = viewFragment.findViewById(R.id.bot_deas_user)
    }

    private fun visibility(){
        logout_user.setVisibility(View.VISIBLE)
        title.setVisibility(View.VISIBLE)
        photo.setVisibility(View.VISIBLE)
        username.setVisibility(View.VISIBLE)
        email.setVisibility(View.VISIBLE)
        date.setVisibility(View.VISIBLE)
        totalPoints.setVisibility(View.VISIBLE)
        points.setVisibility(View.VISIBLE)
        logout_user.setVisibility(View.VISIBLE)
        button_deas.setVisibility(View.VISIBLE)
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
                prefs.setUsername("")
                prefs.setEmail("")
                prefs.saveDate("")
                startActivity(intent)
            }
        }
    }

}