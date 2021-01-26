package com.kforce.urbanic.service.content

import android.accounts.NetworkErrorException
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kforce.urbanic.extension.buildURL
import com.kforce.urbanic.repository.main.Resource
import com.kforce.urbanic.repository.settings.BaseSettings
import com.kforce.urbanic.service.main.baseGetRequest
import com.kforce.urbanic.util.REQUEST_SUCCEEDED
import java.lang.Exception


class ContentService {

    companion object {
        private const val BASE_MOCK_API_URL = "https://6008f29a0a54690017fc2777.mockapi.io/api "
        private const val API_VERSION = "beta "
        private const val SETTINGS_ENDPOINT = "settings"
    }

    suspend fun fetchSettings(): Resource<BaseSettings> {
        val targetURL = BASE_MOCK_API_URL + API_VERSION + SETTINGS_ENDPOINT
        val requestResponse = baseGetRequest(targetURL.buildURL())
        return when (requestResponse.code){
            REQUEST_SUCCEEDED -> {
                val baseSettings: BaseSettings = GsonBuilder().create().fromJson(requestResponse.body, BaseSettings::class.java)
                Resource(status = Resource.Status.SUCCESS, data = baseSettings, throwable = null)
            }
            else -> Resource(
                status = Resource.Status.ERROR, data = null, throwable = NetworkErrorException(
                    requestResponse.exception
                )
            )
        }
    }
}