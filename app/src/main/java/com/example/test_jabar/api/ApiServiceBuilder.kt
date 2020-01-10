package com.example.test_jabar.api

import com.example.test_jabar.helper.Constant
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiServiceBuilder {

    fun provideApiService(): ApiService {
        return provideRetrofit(Constant.KEY_API_URL).create(ApiService::class.java)
    }
    private fun provideRetrofit(baseUrl: String): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(provideOkHttpClient())
                .build()
    }
    private fun provideOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        return httpClient
                .addInterceptor(provideInterceptor())
                .addInterceptor(provideInterceptorWithHttpLogging())
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS).build()
    }
    private fun provideInterceptorWithHttpLogging(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    private fun provideInterceptor(): (Interceptor.Chain) -> Response {
        return { chain: Interceptor.Chain ->
            val originalRequest = chain.request()
            val request = originalRequest.newBuilder().build()
            chain.proceed(request)
        }
    }
}