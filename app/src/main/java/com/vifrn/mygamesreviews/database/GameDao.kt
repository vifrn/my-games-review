package com.vifrn.mygamesreviews.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vifrn.mygamesreviews.Constants
import com.vifrn.mygamesreviews.model.Game

@Dao
interface GameDao {

    @Query("SELECT * FROM ${Constants.GAME_TABLE_NAME}")
    fun getCachedGames () : LiveData<List<Game>>

    @Query("SELECT * FROM ${Constants.GAME_TABLE_NAME} WHERE myReview IS NOT NULL")
    fun getReviewedGames () : LiveData<List<Game>>

    @Query("SELECT * FROM ${Constants.GAME_TABLE_NAME} WHERE id = :id")
    fun getGameById (id : Int) : LiveData<Game>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertGames (vararg game: Game)

}