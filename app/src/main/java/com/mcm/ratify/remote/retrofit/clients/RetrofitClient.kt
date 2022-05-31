package com.mcm.ratify.remote.retrofit.clients

import com.mcm.ratify.remote.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class RetrofitClient(baseUrl: String){

   protected val clientInstance: Retrofit = Retrofit.Builder()
                   .baseUrl(baseUrl)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build()

    abstract fun createService() : ApiService
}