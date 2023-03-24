package com.codecamp.fitnessapp.ui.screens.result

import androidx.lifecycle.ViewModel
import com.codecamp.fitnessapp.data.track.DefaultTrackRepository
import com.codecamp.fitnessapp.model.Track
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ResultViewModel
@Inject constructor(
    private val trackRepository: DefaultTrackRepository
) : ViewModel() {
    val tracklist = trackRepository.tracks

    fun getLatlngList(tracklist: List<Track>): List<LatLng> {
        val latlngList = mutableListOf<LatLng>()
        for (track in tracklist) {
            latlngList.add(LatLng(track.lat, track.long))
        }
        return latlngList
    }

    fun getAltitudeList(tracklist: List<Track>): List<Pair<Int, Float>> {
        val altitudeList = mutableListOf<Pair<Int, Float>>()
        for (track in tracklist) {
            altitudeList.add(Pair(track.timestamp.toInt(), track.altitude.toFloat()))
        }
        return altitudeList
    }

    fun getUsedTracks(tracklist: List<Track>, id: Int): List<Track> {
        val usedTracks = mutableListOf<Track>()
        for (track in tracklist) {
            if (track.workoutId == id) {
                usedTracks.add(track)
            }
        }
        return usedTracks
    }
}