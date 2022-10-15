package com.example.amlode

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.preference.PreferenceManager

object SavedPreference {

    const val EMAIL = "email"
    const val USERNAME = "username"
    var PHOTO= "photo"

    fun getSharedPreference(ctx: Context?): SharedPreferences? {
        return PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    private fun editor(context: Context, const:String, string: String){
        getSharedPreference(
            context
        )?.edit()?.putString(const,string)?.apply()
    }

    private fun editorPhoto(context: Context, const:String, uri: Uri?){
        getSharedPreference(
            context
        )?.edit()?.putString(const,uri.toString())?.apply()
    }

    fun getEmail(context: Context)= getSharedPreference(
        context
    )?.getString(EMAIL,"")

    fun setEmail(context: Context, email: String){
        editor(
            context,
            EMAIL,
            email
        )
    }

    fun setUsername(context: Context, username:String){
        editor(
            context,
            USERNAME,
            username
        )
    }

    fun getUsername(context: Context) = getSharedPreference(
        context
    )?.getString(USERNAME,"")


    fun getPhoto(): String {
        return this.PHOTO
    }


    fun setPhoto(photo: Uri?){
        PHOTO = photo.toString()
    }
}




