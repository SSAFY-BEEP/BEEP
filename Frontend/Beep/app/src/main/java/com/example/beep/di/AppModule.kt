package com.example.beep.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.beep.data.repository.AuthRepository
import com.example.beep.data.repository.AuthRepositoryImpl
import com.example.beep.network.api.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
                .baseUrl("https://k7a406.p.ssafy.io:8081/api/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create()
    }

    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi, prefs: SharedPreferences): AuthRepository {
        return AuthRepositoryImpl(api, prefs)
    }
}