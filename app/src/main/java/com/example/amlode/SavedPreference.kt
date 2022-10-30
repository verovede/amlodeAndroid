package com.example.amlode

import android.content.Context
import android.net.Uri

class SavedPreference(val context: Context) {

    private val SHARED_NAME = "amlode"
    private val EMAIL = "email"
    private val USERNAME = "username"
    private var PHOTO= "photo"
    private var DATE= "date"
    private var SIZEDEAS= "size"
    private var POINTS= "points"

    val storage = context.getSharedPreferences(SHARED_NAME,0)

    fun setEmail(email: String){
        storage.edit().putString(EMAIL,email).apply()
    }

    fun getEmail():String{
        return  storage.getString(EMAIL, "")!!
    }

    fun setUsername(username:String){
        storage.edit().putString(USERNAME,username).apply()
    }

    fun getUsername():String{
        return storage.getString(USERNAME, "")!!
    }

    fun getPhoto(): String {
        return storage.getString(PHOTO, "")!!
    }

    fun savePhoto(photo: Uri?){
        storage.edit().putString(PHOTO, photo.toString()).apply()
    }

    fun getDate(): String {
        return storage.getString(DATE, "")!!
    }

    fun saveDate(date: String){
        storage.edit().putString(DATE, date).apply()
    }

    fun getSizeDeas(): Int {
        return storage.getInt(SIZEDEAS, 0)
    }

    fun saveSizeDeas(size: Int){
        storage.edit().putInt(SIZEDEAS, size).apply()
    }

    fun getPoints(): Int {
        return storage.getInt(POINTS, 0)
    }

    fun savePoints(size: Int){
        storage.edit().putInt(POINTS, size).apply()
    }
}




