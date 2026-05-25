package com.veltra.payment.network

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

object ApiClient {
    private val client = OkHttpClient()
    private val gson = Gson()
    private const val BASE_URL = "http://10.0.2.2:8080/v1" // 10.0.2.2 points to localhost from Android Emulator

    fun post(path: String, body: Any, callback: (Boolean, String?) -> Unit) {
        val json = gson.toJson(body)
        val requestBody = json.toRequestBody("application/json".toMediaType())
        
        val request = Request.Builder()
            .url("$BASE_URL$path")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                mainThread { callback(false, e.message) }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                mainThread { callback(response.isSuccessful, responseData) }
            }
        })
    }

    fun get(path: String, callback: (Boolean, String?) -> Unit) {
        val request = Request.Builder()
            .url("$BASE_URL$path")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                mainThread { callback(false, e.message) }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                mainThread { callback(response.isSuccessful, responseData) }
            }
        })
    }

    private fun mainThread(work: () -> Unit) {
        Handler(Looper.getMainLooper()).post(work)
    }
}
