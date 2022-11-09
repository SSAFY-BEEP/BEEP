package com.example.beep.di

import com.example.beep.network.api.*
import com.example.beep.util.AuthInterceptor
import com.example.beep.util.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {
    @Provides
    @Singleton
    @Named("retrofit")
    fun provideRetrofitInstance(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).client(client)
            .build()
    }

    // Gson DI
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    // RetrofitApi DI
    @Provides
    @Singleton
    fun provideRetrofitApi(@Named("retrofit") retrofit: Retrofit): RetrofitApi {
        return retrofit.create(RetrofitApi::class.java)
    }

    @Provides
    @Singleton
    fun providePresetApi(@Named("retrofit") retrofit: Retrofit): PresetApi {
        return retrofit.create(PresetApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAddressApi(@Named("retrofit") retrofit: Retrofit): AddressApi {
        return retrofit.create(AddressApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMessageApi(@Named("retrofit") retrofit: Retrofit): MessageApi {
        return retrofit.create(MessageApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMessage24Api(@Named("retrofit") retrofit: Retrofit): Message24Api {
        return retrofit.create(Message24Api::class.java)
    }

    @Provides
    @Singleton
    fun provideS3Api(@Named("retrofit") retrofit: Retrofit): S3Api {
        return retrofit.create(S3Api::class.java)
    }

    // OkHttpClient DI
    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    // HttpLoggingInterceptor DI
    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}