package com.vifrn.mygamesreviews.network

import com.vifrn.mygamesreviews.BuildConfig
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(NetworkConstants.API_URL)
    .build()

object IgdbApi {
    val retrofitService : IgdbApiService by lazy {
        retrofit.create(IgdbApiService::class.java)
    }
}

interface IgdbApiService {
    @POST(NetworkConstants.GAMES_ENDPOINT)
    @Headers(
        "Client-ID: ${BuildConfig.IGDB_CLIENT_ID}"
    )
    fun getGames(
        @Header("Authorization") token : String,
        @Body body : String = NetworkConstants.FIELDS_FILTER) : Call<String>
}