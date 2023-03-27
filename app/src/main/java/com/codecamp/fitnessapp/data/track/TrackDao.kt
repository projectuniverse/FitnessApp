package com.codecamp.fitnessapp.data.track

import androidx.room.*
import com.codecamp.fitnessapp.model.Track
import kotlinx.coroutines.flow.Flow

/*
 * Makes the requests to the Room database
 */
@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(track: Track)

    @Query("SELECT * FROM track WHERE id = :id")
    fun getTrack(id: Int): Flow<Track>

    @Query("SELECT * FROM track")
    fun getAllTracks(): Flow<List<Track>>

    @Query("DELETE FROM track")
    fun deleteTracks()
}