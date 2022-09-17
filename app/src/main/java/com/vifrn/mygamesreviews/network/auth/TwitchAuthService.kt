package com.vifrn.mygamesreviews.network.auth

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vifrn.mygamesreviews.BuildConfig
import com.vifrn.mygamesreviews.network.NetworkConstants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query

//REF: https://medium.com/android-news/token-authorization-with-retrofit-android-oauth-2-0-747995c79720

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(NetworkConstants.AUTH_URL)
    .build()

object TwitchAuthApi {
    val retrofitService : TwitchAuthService by lazy {
        retrofit.create(TwitchAuthService::class.java)
    }
}

interface TwitchAuthService {
    @POST(NetworkConstants.TOKEN_ENDPOINT)
    fun auth (@Query("client_id") clientId : String = BuildConfig.IGDB_CLIENT_ID,
              @Query("client_secret") clientSecret : String = BuildConfig.IGDB_CLIENT_SECRET,
              @Query("grant_type") grantType : String = NetworkConstants.GRANT_TYPE) : Call<AuthResponse>
}