package com.dicoding.racode.jakartasweatherprediction.network

import com.dicoding.racode.jakartasweatherprediction.model.WeatherResponse
import com.dicoding.racode.jakartasweatherprediction.utils.Constant
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface EndPoint {

    @GET("/data/2.5/forecast")
    fun getPostsAsync(
        @Query("units") units: String = "metric",
        @Query("id") id: String = Constant.Table.CITY_ID
    ): Deferred<WeatherResponse>

    companion object {
        operator fun invoke(): EndPoint {
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter(Constant.Table.API_KEY, Constant.Table.API_KEY_VALUE)
                    .build()

                val request = chain
                    .request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient
                .Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constant.Table.BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EndPoint::class.java)
        }
    }

}