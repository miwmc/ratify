package com.mcm.ratify.remote.retrofit.services

import com.mcm.ratify.data.remote.dto.Chart
import com.mcm.ratify.data.remote.dto.GenreList
import com.mcm.ratify.data.remote.dto.TrackList
import com.mcm.ratify.remote.ApiService
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface DeezerService : ApiService {

    @GET("genre")
    suspend fun getGenreList() : GenreList

    @GET("chart/{id}")
    suspend fun getAlbumList(
        @Path("id") id: Int,
        @Query("index") index: Int,
        @Query("limit") limit: Int,
    ) : Chart

    @GET("album/{id}/tracks")
    suspend fun getTrackList(
        @Path("id") id: Long,
        @Query("index") index: Int,
        @Query("limit") limit: Int
    ) : TrackList
}