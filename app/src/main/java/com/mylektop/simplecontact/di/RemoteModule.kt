package com.mylektop.simplecontact.di

import com.mylektop.simplecontact.BuildConfig
import com.mylektop.simplecontact.apiservice.ContactService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by MyLektop on 23/10/2019.
 */
val RemoteModule = applicationContext {
    bean { createOkHttpClient() }
    bean { createWebService<ContactService.GET>(get()) }
    bean { createWebService<ContactService.POST>(get()) }
    bean { createWebService<ContactService.DELETE>(get()) }
    bean { createWebService<ContactService.PUT>(get()) }
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    return retrofit.create(T::class.java)
}

fun createOkHttpClient(): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)

    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    httpClient.addInterceptor(logging)

    return httpClient.build()
}