package com.vifrn.mygamesreviews.repository

import android.util.Log
import com.vifrn.mygamesreviews.network.igdb.IgdbApi
import com.vifrn.mygamesreviews.network.parseGamesJsonArray
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GamesRepository {

    suspend fun getGamesSuggestions (token : String) {
        val obj = IgdbApi.retrofitService.getGames(token)
        Log.d("TEST", "URL: ${obj.request().url}")

        for(header in obj.request().headers) {
            Log.d("TEST", "Header: $header")
        }

        IgdbApi.retrofitService.getGames(token).enqueue(object: Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("NW test", "Something went wrong: ${t.message}")
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val gameList = parseGamesJsonArray(JSONArray(response.body()))
                    Log.d("NW test", "Success - Game List: $gameList")
                } else {
                    try {
                        val jObjError = JSONObject(response.errorBody()?.toString())
                        Log.d("Error", jObjError.getJSONObject("error").getString("message"))
                    } catch (e : Exception) {
                        Log.d("Error", "Unable to retrieve data from error body")
                    }
                    Log.d("NW test", "API returned code response: ${response.code()} ${response.body()} ${response.errorBody()} ${response.message()}")
                }

            }
        })
    }


}