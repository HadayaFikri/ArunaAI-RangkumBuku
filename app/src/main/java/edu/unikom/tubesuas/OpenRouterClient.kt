package edu.unikom.tubesuas

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

object OpenRouterClient {
    private val client = OkHttpClient()

    private const val API_URL = "Masukan URl"
    private const val API_KEY = "Masukan API"

    fun kirimPrompt(prompt: String, callback: (String?) -> Unit) {
        val jsonBody = JSONObject()
        jsonBody.put("model", "openai/gpt-3.5-turbo")

        val messages = JSONArray()
        val userMessage = JSONObject()
        userMessage.put("role", "user")
        userMessage.put("content", prompt)
        messages.put(userMessage)

        jsonBody.put("messages", messages)

        val requestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            jsonBody.toString()
        )

        val request = Request.Builder()
            .url(API_URL)
            .addHeader("Authorization", API_KEY)
            .addHeader("Content-Type", "application/json")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) {
                        callback("Gagal: ${it.code}")
                        return
                    }

                    val responseBody = it.body?.string()
                    val json = JSONObject(responseBody)
                    val resultText = json
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")

                    callback(resultText)
                }
            }
        })
    }
}
