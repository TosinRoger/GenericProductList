package com.tosin.genericproductlist.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val PRODUCT_LIST = "ProductList"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = PRODUCT_LIST
)

class SettingsDataStore(preferenceDatastore: DataStore<Preferences>) {
    private val productIdClicked = intPreferencesKey("product_id_clicked")

    val preferenceFlow: Flow<Int> = preferenceDatastore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            // On the first run of the app, we will use LinearLayoutManager by default
            preferences[productIdClicked] ?: 0
        }

    suspend fun saveProductIdClickedToPreferencesStore(productId: Int, context: Context) {
        context.dataStore.edit { preferences ->
            preferences[productIdClicked] = productId
        }
    }
}
