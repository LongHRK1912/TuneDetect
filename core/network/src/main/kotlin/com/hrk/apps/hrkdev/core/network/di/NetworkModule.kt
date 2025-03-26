package com.hrk.apps.hrkdev.core.network.di

import android.content.Context
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import com.google.gson.GsonBuilder
import com.hrk.apps.hrkdev.core.network.BuildConfig
import com.hrk.apps.hrkdev.core.network.manager.EnvironmentManager
import com.hrk.apps.hrkdev.core.network.service.ApiService
import com.hrk.apps.hrkdev.core.network.service.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
        return OkHttpClient.Builder().addInterceptor(logging).addInterceptor(
            Interceptor { chain ->
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
                    .header("Cache-Control", "max-age=0, no-cache, no-store, must-revalidate")
                    .build()
                chain.proceed(newRequest)
            }
        ).connectTimeout(RetrofitService.LONG_TIME_OUT, TimeUnit.MILLISECONDS).readTimeout(
            RetrofitService.LONG_TIME_OUT,
            TimeUnit.SECONDS
        )
            .writeTimeout(15, TimeUnit.SECONDS).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder().client(okHttpClient)
            .baseUrl(EnvironmentManager.currentEnvironment.url)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create()

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun imageLoader(
        okHttpCallFactory: Call.Factory,
        @ApplicationContext application: Context
    ): ImageLoader = ImageLoader.Builder(application)
        .callFactory(okHttpCallFactory)
        .components {
            add(SvgDecoder.Factory())
        }
        .respectCacheHeaders(false)
        .memoryCachePolicy(CachePolicy.ENABLED).memoryCache {
            MemoryCache.Builder(application)
                .maxSizePercent(0.25)
                .strongReferencesEnabled(true)
                .build()
        }
        .diskCachePolicy(CachePolicy.ENABLED)
        .diskCache {
            DiskCache.Builder()
                .maxSizePercent(0.05)
                .directory(application.cacheDir)
                .build()
        }
        .build()
}
