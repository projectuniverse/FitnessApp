package com.codecamp.fitnessapp.data.weather

import androidx.room.*
import com.codecamp.fitnessapp.model.Weather
import kotlinx.coroutines.flow.Flow

/*
 * Makes the requests to the Room database
 */
@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: Weather)

    @Query("SELECT * FROM weather WHERE id = 0")
    fun getWeather(): Flow<Weather?>
}