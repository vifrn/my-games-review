package com.vifrn.mygamesreviews.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.vifrn.mygamesreviews.database.GameDatabase
import com.vifrn.mygamesreviews.model.Game
import com.vifrn.mygamesreviews.network.NetworkConstants
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

    private val foundGames = MutableLiveData<List<Int>>()
    val searchedGames = Transformations.switchMap(foundGames) {
        database.gameDao.getGamesByIds(it)
    }

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
                logError(e)
            }
        }
    }

    suspend fun searchGame (name : String, token : String) {
        withContext(Dispatchers.IO) {
            try {
                val games = parseGamesJsonArray(
                    JSONArray(
                        IgdbApi.retrofitService.searchGame(token, "search \"$name\"; ${NetworkConstants.FIELDS_FILTER} ${NetworkConstants.LIMIT_10}").await()
                    )
                )
                database.gameDao.insertGames(*games)

                val ids = mutableListOf<Int>()
                for (game in games) {
                    ids.add(game.id)
                }
                foundGames.postValue(ids)
            } catch (e : Exception) {
                logError(e)
            }
        }
    }

    suspend fun updateGame (game : Game) {
        withContext(Dispatchers.IO) {
            try {
                database.gameDao.updateGame(game)
            } catch (e : Exception) {
                logError(e)
            }
        }
    }

    private fun logError (e : Exception) {
        Log.e("Failure", "Something went wrong: " + e.message)
    }


}