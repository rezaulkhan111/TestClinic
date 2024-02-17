package com.example.testclinic.di

import android.os.Build
import com.example.testclinic.data.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    private val baseUrl: String = "https://gorest.co.in/public/v2/"
    private val token: String =
        "Bearer 5f54c461fb7d6b345b3a770fa4586795ec1125bb80459ed9daba5f1c26cf26e0"

    @Provides
    fun provideRetrofit(): ApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder().apply {
                    addNetworkInterceptor {
                        it.proceed(
                            it.request().newBuilder()
                                .header(
                                    "Authorization",
                                    token
                                ).build()
                        )
                    }
                }.build()
            )
            .build()
            .create(ApiService::class.java)
    }
}