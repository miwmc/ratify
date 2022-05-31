package com.mcm.ratify.remote.retrofit.clients

import com.mcm.ratify.remote.ApiService
import com.mcm.ratify.remote.retrofit.services.DeezerService

class DeezerClient(baseUrl: String) : RetrofitClient(baseUrl) {

    override fun createService(): ApiService {
        return clientInstance.create(DeezerService::class.java)
    }
}