package com.vifrn.mygamesreviews.database

import androidx.lifecycle.LiveData
import androidx.room.*
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

    @Query("SELECT * FROM ${Constants.GAME_TABLE_NAME} WHERE id IN (:ids)")
    fun getGamesByIds(ids: List<Int>): LiveData<List<Game>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGames (vararg game: Game)

    @Update
    fun updateGame (game : Game)

}