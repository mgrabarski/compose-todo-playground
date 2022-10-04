package grabarski.mateusz.todo_compose.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import grabarski.mateusz.todo_compose.data.models.Priority
import grabarski.mateusz.todo_compose.utils.Constants.PREFERENCES_NAME
import grabarski.mateusz.todo_compose.utils.Constants.PREFERENCES_SORT_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStoreRepository(
    context: Context
) {

    private object PreferencesKeys {
        val sortKey = stringPreferencesKey(name = PREFERENCES_SORT_KEY)
    }

    private val dataStore = context.dataStore

    suspend fun persistSortState(priority: Priority) {
        dataStore.edit {
            it[PreferencesKeys.sortKey] = priority.name
        }
    }

    val readSortState: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {
            it[PreferencesKeys.sortKey] ?: Priority.NONE.name
        }
}
