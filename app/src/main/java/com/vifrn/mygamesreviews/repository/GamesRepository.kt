package com.vifrn.mygamesreviews.repository

import android.util.Log
import com.vifrn.mygamesreviews.database.GameDatabase
import com.vifrn.mygamesreviews.network.igdb.IgdbApi
import com.vifrn.mygamesreviews.network.parseGamesJsonArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await

class GamesRepository (private val database : GameDatabase) {

    val suggestions = database.gameDao.getCachedGames()
    val myReviews = database.gameDao.getReviewedGames()

    suspend fun refreshSuggestions (token : String) {
        withContext(Dispatchers.IO) {
            try {
                val games = parseGamesJsonArray(
                    JSONArray(
                        IgdbApi.retrofitService.getGames(token).await()
                    )
                )
                database.gameDao.insertGames(*games)
            } catch (e : Exception) {
                Log.e("Failure", "Something went wrong: " + e.message)
            }
        }
    }


}