package com.vifrn.mygamesreviews.network.igdb

import com.vifrn.mygamesreviews.BuildConfig
import com.vifrn.mygamesreviews.network.NetworkConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

private val interceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)
private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(NetworkConstants.API_URL)
    .client(client)
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
        @Body body : String = NetworkConstants.FIELDS_FILTER + NetworkConstants.LIMIT_50) : Call<String>
}