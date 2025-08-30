package com.example.paymob.data.cache

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class CacheManager @Inject constructor(val gson: Gson,val prefs: SharedPreferences) {
    fun <T> saveToCache(key: String, data: T) {
        val json = gson.toJson(data)
        prefs.edit().apply {
            putString(key, json)
        }.apply()
    }

    fun <T> getFromCache(key: String, typeToken: TypeToken<T>): T? {
        val json = prefs.getString(key, null) ?: return null
        return gson.fromJson(json, typeToken.type)
    }

    fun clearCache() {
        prefs.edit().clear().apply()
    }

    fun hasCache(key: String): Boolean{
        val json = prefs.getString(key, null)
        return !(json.isNullOrBlank())
    }
}