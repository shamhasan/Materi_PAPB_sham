package ih.pam.tobearsitekturpapb.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(
    private val context: Context,
) {
    private val lastSearchQueryKey = stringPreferencesKey("last_search_query")
    private val sortOrderKey = stringPreferencesKey("sort_order")

    val lastSearchQuery: Flow<String>
        get() = context.userPreferencesDataStore.data.map { preferences ->
            preferences[lastSearchQueryKey] ?: ""
        }

    val lastSortOrder: Flow<String>
        get() = context.userPreferencesDataStore.data.map { preferences ->
            preferences[sortOrderKey] ?: "ASC"
        }

    suspend fun saveLastSortOrder(value: String) {
        context.userPreferencesDataStore.edit { preferences ->
            preferences[sortOrderKey] = value
        }
    }

    suspend fun saveLastSearchQuery(value: String) {
        context.userPreferencesDataStore.edit { preferences ->
            preferences[lastSearchQueryKey] = value
        }
    }

}