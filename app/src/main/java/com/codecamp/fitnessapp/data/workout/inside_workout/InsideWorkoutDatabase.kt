package com.codecamp.fitnessapp.data.workout.inside_workout

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codecamp.fitnessapp.model.*

/*
 * Acts as the Room database for inside workout data
 */
@Database(entities = [InsideWorkout::class], version = 1, exportSchema = false)
abstract class InsideWorkoutDatabase : RoomDatabase() {
    abstract fun insideWorkoutDao(): InsideWorkoutDao
    companion object {
        @Volatile
        private var Instance: InsideWorkoutDatabase? = null
        fun getDatabase(context: Context): InsideWorkoutDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InsideWorkoutDatabase::class.java, "inside_workout_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it}
            }
        }
    }
}