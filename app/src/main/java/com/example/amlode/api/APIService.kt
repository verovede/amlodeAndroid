package com.example.amlode.api
import com.example.amlode.intefaces.deaAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor.Level

class APIService {

    companion object {
        private const val BASE_URL = ""

        fun create( baseURL: String): deaAPI {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(deaAPI::class.java)
        }
    }
}
