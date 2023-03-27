package com.codecamp.fitnessapp.data.Initialization

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/*
 * Acts as a single source of truth. Updates the DataStore for first initialization.
 */
interface InitializationRepository {
    suspend fun updateFirstInit()
    suspend fun getFirstInit() : Boolean
}

@kotlinx.serialization.ExperimentalSerializationApi
class DefaultInitializationRepository
    @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : InitializationRepository {
    private val isFirstTimeLaunchKey = booleanPreferencesKey("is_first_time")

    override suspend fun updateFirstInit() {
        dataStore.edit { preferences ->
            preferences[isFirstTimeLaunchKey] = false
        }
    }

    override suspend fun getFirstInit(): Boolean {
        val isFirstTimeLaunchFlow: Flow<Boolean> = dataStore.data
            .map { preferences -> preferences[isFirstTimeLaunchKey] ?: true }
            .distinctUntilChanged()
        return isFirstTimeLaunchFlow.first()
    }
}