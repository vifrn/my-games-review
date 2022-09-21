package com.vifrn.mygamesreviews.review.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vifrn.mygamesreviews.database.getDatabase
import com.vifrn.mygamesreviews.repository.GamesRepository

class GameListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GamesRepository(getDatabase(application))

    val myReviews = repository.myReviews
}