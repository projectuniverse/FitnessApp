package com.codecamp.fitnessapp.ui.screens.result

import android.util.Log
import androidx.lifecycle.ViewModel
import com.codecamp.fitnessapp.data.track.DefaultTrackRepository
import com.codecamp.fitnessapp.data.workout.DefaultWorkoutRepository
import com.codecamp.fitnessapp.model.Track
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ResultViewModel
@Inject constructor(
    private val workoutRepository: DefaultWorkoutRepository,
    private val trackRepository: DefaultTrackRepository
) : ViewModel() {
    val tracklist = trackRepository.tracks
    val workouts = workoutRepository.outsideWorkouts
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
                usedTracks.add(track.copy(timestamp = track.timestamp/1000))
                Log.d("MYTA", "getUsedTracks: ${track.timestamp} : ${track.altitude}")
            }
        }
        return usedTracks
    }

    fun formatTime(doubleMinutes: Double): String {
        val minutes = doubleMinutes.toInt()
        val seconds = ((doubleMinutes - minutes) * 60).toInt()
        return String.format("%02d:%02d", minutes, seconds)
    }

    fun getElapsedTime(endTime: Int, startTime: Int): String {
        val elapsedSeconds = endTime - startTime
        val elapsedMinutes = elapsedSeconds / 60
        val elapsedHours = elapsedMinutes / 60

        val secondsDisplay = if(elapsedSeconds % 60 < 10) { "0" + elapsedSeconds % 60 }
        else { elapsedSeconds % 60 }
        val minutsDisplay = if(elapsedMinutes % 60 < 10) { "0" + elapsedMinutes % 60 }
        else { elapsedMinutes % 60 }
        val hoursDisplay = if(elapsedHours < 10) { "0" + elapsedHours % 60 }
        else { ""+elapsedHours }

        return "$hoursDisplay:$minutsDisplay:$secondsDisplay"
    }
}