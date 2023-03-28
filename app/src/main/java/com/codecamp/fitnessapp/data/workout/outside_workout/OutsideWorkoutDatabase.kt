package com.codecamp.fitnessapp.data.workout.outside_workout

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codecamp.fitnessapp.model.*

/*
 * Acts as the Room database for outside workout data
 */
@Database(entities = [OutsideWorkout::class], version = 1, exportSchema = false)
abstract class OutsideWorkoutDatabase : RoomDatabase() {
    abstract fun outsideWorkoutDao(): OutsideWorkoutDao
    companion object {
        @Volatile
        private var Instance: OutsideWorkoutDatabase? = null
        fun getDatabase(context: Context): OutsideWorkoutDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, OutsideWorkoutDatabase::class.java, "outside_workout_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it}
            }
        }
    }
}