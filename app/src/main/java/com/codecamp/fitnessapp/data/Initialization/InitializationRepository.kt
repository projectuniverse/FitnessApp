package com.codecamp.fitnessapp.data.Initialization

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/*
 * Acts as a single source of truth. Updates the DataStore for first initialization.
 */
interface InitializationRepository {
    suspend fun setFirstInit(firstInit : Boolean)
    suspend fun getFirstInit()
}

@kotlinx.serialization.ExperimentalSerializationApi
class DefaultInitializationRepository
    @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : InitializationRepository {
    private val isFirstTimeLaunchKey = booleanPreferencesKey("is_first_time")

    var firstInit = MutableStateFlow<Boolean?>(null)

    override suspend fun setFirstInit(firstInit : Boolean) {
        dataStore.edit { preferences ->
            preferences[isFirstTimeLaunchKey] = firstInit
        }
        this.firstInit.value = firstInit
    }

    override suspend fun getFirstInit() {
        val isFirstTimeLaunchFlow: Flow<Boolean> = dataStore.data
            .map { preferences -> preferences[isFirstTimeLaunchKey] ?: true }
            .distinctUntilChanged()
        firstInit.value = isFirstTimeLaunchFlow.first()
    }
}