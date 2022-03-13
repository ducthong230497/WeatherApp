package com.weather.app.utils

import android.content.SharedPreferences
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

private fun <T> asFlow(sharedPrefs: SharedPreferences, key: String, get: ((key: String) -> T)): Flow<T> = callbackFlow {
    trySend(get.invoke(key))
    val preferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, k ->
        if (key == k) {
            this@callbackFlow.trySend(get.invoke(key))
        }
    }
    sharedPrefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    awaitClose {
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
    }
}

fun SharedPreferences.getBoolean(key: String) = this.getBoolean(key, false)
fun SharedPreferences.booleanFlow(key: String) = asFlow<Boolean>(this, key, this::getBoolean)
