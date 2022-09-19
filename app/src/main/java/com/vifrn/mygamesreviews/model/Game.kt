package com.vifrn.mygamesreviews.model

data class Game (
    val id : Int,
    val name : String,
    val summary : String?,
    val smallImageUrl : String?,
    val bigImageUrl : String?,
    val myRating : Int?,
    val myReview : String?
)