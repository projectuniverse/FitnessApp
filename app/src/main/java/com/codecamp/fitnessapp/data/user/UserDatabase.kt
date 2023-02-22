package com.codecamp.fitnessapp.data.user

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codecamp.fitnessapp.data.weather.WeatherDao
import com.codecamp.fitnessapp.model.*

/*
 * Acts as the Room database for offline weather
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object {
        @Volatile
        private var Instance: UserDatabase? = null
        fun getDatabase(context: Context): UserDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, UserDatabase::class.java, "user_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it}
            }
        }
    }
}