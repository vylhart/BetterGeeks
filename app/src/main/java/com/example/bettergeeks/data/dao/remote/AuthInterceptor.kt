package com.example.bettergeeks.data.dao.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class AuthInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $apiKey")
            .build()
        return chain.withConnectTimeout(300000, TimeUnit.MILLISECONDS).proceed(modifiedRequest)
    }
}
