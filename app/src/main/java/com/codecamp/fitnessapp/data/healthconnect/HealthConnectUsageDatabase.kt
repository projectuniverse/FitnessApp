package com.codecamp.fitnessapp.data.healthconnect

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codecamp.fitnessapp.model.healthconnect.HealthConnectUsage


@Database(entities = [HealthConnectUsage::class], version = 1, exportSchema = false)
abstract class HealthConnectUsageDatabase: RoomDatabase() {
    abstract fun healthConnectUsageDao(): HealthConnectUsageDao

    companion object {
        @Volatile
        private var Instance: HealthConnectUsageDatabase? = null
        fun getDatabase(context: Context): HealthConnectUsageDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, HealthConnectUsageDatabase::class.java, "healthconnectusage_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}