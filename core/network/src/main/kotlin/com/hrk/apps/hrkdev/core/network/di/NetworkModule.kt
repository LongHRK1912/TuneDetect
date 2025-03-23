package com.hrk.apps.hrkdev.core.network.di

import com.hrk.apps.hrkdev.core.network.manager.EnvironmentManager
import com.hrk.apps.hrkdev.core.network.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MainInterceptor


    @MainInterceptor
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @MainInterceptor
    @Provides
    @Singleton
    fun provideRetrofit(@MainInterceptor okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(EnvironmentManager.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @MainInterceptor
    @Provides
    @Singleton
    fun provideApiService(@MainInterceptor retrofit: Retrofit): ApiService = retrofit.create()
}
