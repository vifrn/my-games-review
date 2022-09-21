package com.vifrn.mygamesreviews.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vifrn.mygamesreviews.Constants
import kotlinx.android.parcel.Parcelize

@Entity(tableName = Constants.GAME_TABLE_NAME)
@Parcelize
data class Game (
    @PrimaryKey val id : Int,
    val name : String,
    val summary : String?,
    val smallImageUrl : String?,
    val bigImageUrl : String?,
    var myRating : Float?,
    var myReview : String?
) : Parcelable