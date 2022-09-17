package com.vifrn.mygamesreviews.suggestions

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.vifrn.mygamesreviews.network.SessionManager
import com.vifrn.mygamesreviews.network.TokenStatus
import com.vifrn.mygamesreviews.repository.GamesRepository
import kotlinx.coroutines.launch

class SuggestionsViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application)
    private val repository = GamesRepository()

    private val _response = MutableLiveData<String>()
    val response : LiveData<String>
        get() = _response

    val newToken = sessionManager.newTokenReady


    init {
        if(!sessionManager.hasValidToken()) {
            sessionManager.getNewToken()
        } else {
            getSuggestions()
        }

    }

    fun clearTokenStatus () {
        sessionManager.clearTokenStatus()
    }

    fun getSuggestions () {
        viewModelScope.launch {
            val token = sessionManager.fetchAuthToken()
            token?.let {
                repository.getGamesSuggestions(it)
            }
        }
    }
}