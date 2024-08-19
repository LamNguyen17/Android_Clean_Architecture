package com.forest.android_clean_architecture.data.config

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class LoggingInterceptor @Inject constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        // Log the request details
        println("Sending request: ${request.url}")
        println("Request headers: ${request.headers}")
        request.body?.let {
            println("Request body: ${it}")
        }

        // Proceed with the request
        val response: Response = chain.proceed(request)

        // Log the response details
        println("Received response for: ${response.request.url}")
        println("Response code: ${response.code}")
        println("Response headers: ${response.headers}")
        response.body?.let {
            val responseBodyString = it.string()
            println("Response body: $responseBodyString")

            // Rebuild the response before returning it because the body can only be read once
            return response.newBuilder()
                .body(okhttp3.ResponseBody.create(it.contentType(), responseBodyString))
                .build()
        }

        return response
    }
}