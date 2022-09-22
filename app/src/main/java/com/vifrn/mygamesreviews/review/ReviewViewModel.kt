package com.vifrn.mygamesreviews.review

import android.app.Application
import androidx.lifecycle.*
import com.vifrn.mygamesreviews.R
import com.vifrn.mygamesreviews.database.getDatabase
import com.vifrn.mygamesreviews.model.Game
import com.vifrn.mygamesreviews.repository.GamesRepository
import com.vifrn.mygamesreviews.review.list.ShakeDetector
import kotlinx.coroutines.launch
import java.lang.Math.round

class ReviewViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GamesRepository(getDatabase(application))
    private val shakeDetector = ShakeDetector(application)

    private val _gameUpdated = MutableLiveData<Boolean>()
    val gameUpdated : LiveData<Boolean>
        get() = _gameUpdated

    private val _shouldDisplayError = MutableLiveData<Boolean>()
    val shouldDisplayError : LiveData<Boolean>
        get() = _shouldDisplayError

    private val capturing = MutableLiveData(false)
    val buttonText = Transformations.map(capturing) { capturing ->
        when(capturing) {
            false -> R.string.review_add_rating_button_label
            true -> R.string.review_stop_rating_button_label
        }
    }
    val shakeAmount = Transformations.map(shakeDetector.shakeAmount) {
        it.coerceIn(0.0f, 5.0f)
    }
    val review = MutableLiveData("")

    fun setBaseInfo(game : Game) {
        shakeDetector.setBaseAmount(game.myRating ?: 0f)
        review.value = game.myReview
    }

    fun saveReview (game : Game) {
        if(review.value.isNullOrEmpty() || shakeAmount.value == null) {
            _shouldDisplayError.value = true
            return
        }

        if(shakeDetector.isCapturing) shakeDetector.stopCaptureShakeMovement()

       viewModelScope.launch {
           game.myRating = shakeAmount.value
           game.myReview = review.value
           repository.updateGame(game)
           _gameUpdated.value = true
        }
    }

    fun toggleCapturingShake() {
        if(!shakeDetector.isCapturing) {
            capturing.value = true
            shakeDetector.startCaptureShakeMovement()
        } else {
            capturing.value = false
            shakeDetector.stopCaptureShakeMovement()
        }

    }

    fun errorWasDisplayed() {
        _shouldDisplayError.value = false
    }

    fun doneNavigating() {
        _gameUpdated.value = false
    }

    override fun onCleared() {
        super.onCleared()

        if(shakeDetector.isCapturing) shakeDetector.stopCaptureShakeMovement()
    }
}