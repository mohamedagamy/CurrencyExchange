package com.example.paymob.data.di

import android.content.Context
import android.content.SharedPreferences
import com.example.paymob.data.cache.CacheManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {
    @Provides
    @Singleton
    fun providePrefs(@ApplicationContext context:Context): SharedPreferences {
        return context.getSharedPreferences("ApiCache", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideCacheManager(gson: Gson,prefs: SharedPreferences): CacheManager {
        return CacheManager(gson, prefs)
    }
}