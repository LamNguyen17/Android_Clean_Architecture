package com.forest.android_clean_architecture.data.config

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Add the Authorization header with the token to the original request
        val modifiedRequest = originalRequest.newBuilder()
            .build()

        return chain.proceed(modifiedRequest)
    }
}