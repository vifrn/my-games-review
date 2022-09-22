package com.vifrn.mygamesreviews.review

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vifrn.mygamesreviews.database.getDatabase
import com.vifrn.mygamesreviews.model.Game
import com.vifrn.mygamesreviews.repository.GamesRepository
import kotlinx.coroutines.launch

class ReviewViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GamesRepository(getDatabase(application))

    private val _gameUpdated = MutableLiveData<Boolean>()
    val gameUpdated : LiveData<Boolean>
        get() = _gameUpdated

    private val _shouldDisplayError = MutableLiveData<Boolean>()
    val shouldDisplayError : LiveData<Boolean>
        get() = _shouldDisplayError

    fun saveReview (game : Game) {
        if(game.myReview.isNullOrEmpty() || game.myRating == null) {
            _shouldDisplayError.value = true
            return
        }

       viewModelScope.launch {
           repository.updateGame(game)
           _gameUpdated.value = true
        }
    }

    fun errorWasDisplayed() {
        _shouldDisplayError.value = false
    }

    fun doneNavigating() {
        _gameUpdated.value = false
    }
}