package com.vifrn.mygamesreviews.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vifrn.mygamesreviews.model.Game

@Database(entities = [Game::class], version = 1)
abstract class GameDatabase : RoomDatabase() {
    abstract val gameDao : GameDao
}

private lateinit var INSTANCE : GameDatabase

fun getDatabase (context : Context) : GameDatabase {
    synchronized(GameDatabase::class.java) {
        if(!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                GameDatabase::class.java,
                "games").build()
        }
    }

    return INSTANCE
}