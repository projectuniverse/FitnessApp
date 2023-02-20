package com.codecamp.fitnessapp.data.workout.inside_workout

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codecamp.fitnessapp.model.*

/*
 * Acts as the Room database for inside workout data
 */
// TODO nochmal gucken was exportSchema ist und ob man noch was anpassen muss wegen Daten ändern und so:
// TODO Kann es conflicts geben??
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
                    //TODO hier gucken muss was mit der Migration angepasst werden
                    .build()
                    .also { Instance = it}
            }
        }
    }
}