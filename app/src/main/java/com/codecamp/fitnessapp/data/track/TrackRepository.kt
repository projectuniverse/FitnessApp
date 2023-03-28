package com.codecamp.fitnessapp.data.track

import com.codecamp.fitnessapp.model.Track

/*
 * Acts as a single source of truth. Returns track data.
 */
interface TrackRepository {
    suspend fun insertTrack(track: Track)
    suspend fun deleteTracks()
}

@kotlinx.serialization.ExperimentalSerializationApi
class DefaultTrackRepository(
    private val trackDao: TrackDao
) : TrackRepository {
    /*
     * Acts as a reference to the tracks stored in the database.
     * This state flow is viewed by the viewmodel.
     */
    val tracks = trackDao.getAllTracks()

    override suspend fun insertTrack(track: Track) = trackDao.insert(track)
    override suspend fun deleteTracks() = trackDao.deleteTracks()
}