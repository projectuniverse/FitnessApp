package com.codecamp.fitnessapp.data.weather

import androidx.room.*
import com.codecamp.fitnessapp.model.Weather
import kotlinx.coroutines.flow.Flow

/*
 * Makes the requests to the Room database
 */
@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(weather: Weather)

    @Update
    suspend fun update(weather: Weather)

    @Query("SELECT * FROM weather WHERE id = :id")
    fun getWeather(id: Int): Flow<Weather>

    @Query("SELECT EXISTS(SELECT * FROM weather)")
    fun weatherExists(): Flow<Boolean>
}