package com.example.amlode.intefaces
import android.widget.Toast
import com.example.amlode.api.APIService
import com.example.amlode.data.DeaResponse
import com.example.amlode.data.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface UserAPI {
    @GET
    fun getUser(@Url url: String): Call<UserResponse?>?
}

/*
private fun callApiUser() {


    val api = APIService.createUserAPI()
    api.getUser("v2/entities/${mail}?type=user")?.enqueue(object : Callback<UserResponse?> {


        override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {

            val response : UserResponse? = (response.body())!!

            if (response != null) {
                //todo logica
            }
        }

        override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
            Toast.makeText (applicationContext,
                "Se ha producido un error ",
                Toast.LENGTH_SHORT)
                .show();
        }
    })
}

*/