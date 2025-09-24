package com.example.prueba1


import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// DataStore p/ este Context (archivo "settings.preferences_pb")
private val Context.dataStore by preferencesDataStore(name = "settings")

class ThemeRepository(private val appContext: Context) {
    private val KEY_DARK = booleanPreferencesKey("dark_theme")

    val isDarkFlow: Flow<Boolean> =
        appContext.dataStore.data.map { prefs -> prefs[KEY_DARK] ?: false }

    suspend fun setDark(enabled: Boolean) {
        appContext.dataStore.edit { it[KEY_DARK] = enabled }
    }
}
