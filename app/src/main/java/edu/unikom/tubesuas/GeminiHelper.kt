package edu.unikom.tubesuas

import android.util.Log
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

object GeminiHelper {

    private const val API_KEY = "AIzaSyDE5mP_dTdgLMsnAFR9a18tVTyqXYWOEQE"
    private const val GEMINI_URL =
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=$API_KEY"

    private val client = OkHttpClient()
    private val json = Json { ignoreUnknownKeys = true }

    fun getAIResponse(inputText: String, callback: (String) -> Unit) {
        val requestBodyJson = buildJsonObject {
            put("contents", buildJsonArray {
                add(buildJsonObject {
                    put("parts", buildJsonArray {
                        add(buildJsonObject {
                            put("text", inputText)
                        })
                    })
                })
            })
        }

        val requestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            json.encodeToString(requestBodyJson)
        )

        val request = Request.Builder()
            .url(GEMINI_URL)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("GeminiHelper", "Request failed: ${e.message}")
                callback("Gagal mengambil respon AI.")
            }

            override fun onResponse(call: Call, response: Response) {
                val bodyString = response.body?.string()
                if (!response.isSuccessful || bodyString == null) {
                    callback("Respon gagal: ${response.code}")
                    return
                }

                try {
                    val jsonObject = json.parseToJsonElement(bodyString).jsonObject
                    val result = jsonObject["candidates"]?.jsonArray?.getOrNull(0)
                        ?.jsonObject?.get("content")?.jsonObject
                        ?.get("parts")?.jsonArray?.getOrNull(0)
                        ?.jsonObject?.get("text")?.jsonPrimitive?.content

                    callback(result ?: "Tidak ada hasil dari AI.")
                } catch (e: Exception) {
                    Log.e("GeminiHelper", "Parsing error: ${e.message}")
                    callback("Terjadi kesalahan saat memproses respon.")
                }
            }
        })
    }
}
