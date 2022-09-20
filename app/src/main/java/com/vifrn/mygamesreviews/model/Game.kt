package com.vifrn.mygamesreviews.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vifrn.mygamesreviews.Constants

@Entity(tableName = Constants.GAME_TABLE_NAME)
data class Game (
    @PrimaryKey val id : Int,
    val name : String,
    val summary : String?,
    val smallImageUrl : String?,
    val bigImageUrl : String?,
    val myRating : Int?,
    val myReview : String?
)