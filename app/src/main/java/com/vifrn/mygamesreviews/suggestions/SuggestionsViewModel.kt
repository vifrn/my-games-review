package com.vifrn.mygamesreviews.suggestions

import android.app.Application
import androidx.lifecycle.*
import com.vifrn.mygamesreviews.database.getDatabase
import com.vifrn.mygamesreviews.network.TokenManager
import com.vifrn.mygamesreviews.repository.GamesRepository
import kotlinx.coroutines.launch

class SuggestionsViewModel(application: Application) : AndroidViewModel(application) {

    private val tokenManager = TokenManager(application)
    private val repository = GamesRepository(getDatabase(application))

    val suggestions = repository.suggestions
    val searchedGames = repository.searchedGames

    val newToken = tokenManager.newTokenReady

    var showSearchResults = false

    init {
        if(!tokenManager.hasValidToken()) {
            tokenManager.getNewToken()
        } else {
            getSuggestions()
        }

    }

    fun clearTokenStatus () {
        tokenManager.clearTokenStatus()
    }

    fun getSuggestions () {
        viewModelScope.launch {
            val token = tokenManager.fetchAuthToken()
            token?.let {
                repository.refreshSuggestions(it)
            }
        }
    }

    fun searchGame (name : String) {
        viewModelScope.launch {
            showSearchResults = true
            val token = tokenManager.fetchAuthToken()
            token?.let {
                repository.searchGame(name, token)
            }
        }
    }

    fun searchCancelled () {
        showSearchResults = false
    }
}