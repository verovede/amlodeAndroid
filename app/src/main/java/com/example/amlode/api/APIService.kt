package com.example.amlode.api
import com.example.amlode.intefaces.DeaAPI
import com.example.amlode.intefaces.PersistentAPI
import com.example.amlode.intefaces.RoutesAPI
import com.example.amlode.intefaces.UserAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor.Level

class APIService {

    companion object {
        private const val BASE_URL = "http://192.168.100.64:1026/"
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

        fun createPersistent(): PersistentAPI {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PersistentAPI::class.java)
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
