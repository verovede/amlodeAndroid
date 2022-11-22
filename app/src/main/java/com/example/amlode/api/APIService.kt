package com.example.amlode.api
import com.example.amlode.api.Url.Companion.MY_BASE_URL
import com.example.amlode.intefaces.DeaAPI
import com.example.amlode.intefaces.RoutesAPI
import com.example.amlode.intefaces.UserAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class APIService {

    companion object {
        private const val BASE_URL = MY_BASE_URL
        fun createDeaAPI(): DeaAPI {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DeaAPI::class.java)
        }

        fun createUserAPI(): UserAPI {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserAPI::class.java)
        }

        fun createRouteAPI(): RoutesAPI {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl( "https://api.openrouteservice.org/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RoutesAPI::class.java)
        }
    }
}