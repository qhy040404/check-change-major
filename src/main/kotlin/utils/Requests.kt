package utils

import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

object Requests {
    private val client = OkHttpClient.Builder()
        .connectTimeout(25, TimeUnit.SECONDS)
        .readTimeout(50, TimeUnit.SECONDS)
        .writeTimeout(50, TimeUnit.SECONDS)
        .cookieJar(object : CookieJar {
            private val cookieStore = mutableListOf<Cookie>()

            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                cookieStore.addAll(cookies)
            }

            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                return cookieStore
            }
        })
        .build()

    fun get(url: String): String {
        val request = Request.Builder()
            .url(url)
            .get()
            .build()
        client.newCall(request).execute().use { response ->
            return response.body!!.string()
        }
    }

    fun post(url: String, form: String, FORM: MediaType): String {
        val body = form.toRequestBody(FORM)
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        client.newCall(request).execute().use { response ->
            return response.body!!.string()
        }
    }

    fun loginPostData(
        id: String,
        passwd: String,
        ltData: String,
        rsa: String,
        execution: String,
    ) =
        "none=on&rsa=$rsa&ul=${id.length}&pl=${passwd.length}&sl=0&lt=$ltData&execution=$execution&_eventId=submit"
}