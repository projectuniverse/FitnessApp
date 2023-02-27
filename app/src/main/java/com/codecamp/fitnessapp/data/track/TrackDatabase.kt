package com.codecamp.fitnessapp.data.track

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codecamp.fitnessapp.model.*

/*
 * Acts as the Room database for track data
 */
@Database(entities = [Track::class, OutsideWorkout::class], version = 1, exportSchema = false)
abstract class TrackDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
    companion object {
        @Volatile
        private var Instance: TrackDatabase? = null
        fun getDatabase(context: Context): TrackDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TrackDatabase::class.java, "track_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it}
            }
        }
    }
}