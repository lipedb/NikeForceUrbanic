package com.kforce.urbanic.service.content

import android.accounts.NetworkErrorException
import com.google.gson.GsonBuilder
import com.kforce.urbanic.extension.buildURL
import com.kforce.urbanic.repository.core.Resource
import com.kforce.urbanic.repository.definition.DefinitionList
import com.kforce.urbanic.repository.settings.BaseSettings
import com.kforce.urbanic.service.main.baseGetRequest
import com.kforce.urbanic.service.main.baseGetWithHeadersRequest
import com.kforce.urbanic.util.REQUEST_SUCCEEDED


class ContentService {

    companion object {
        private const val BASE_MOCK_API_URL = "https://6008f29a0a54690017fc2777.mockapi.io/api "
        private const val BASE_API_URL = "http://api.urbandictionary.com "
        private const val BASE_RAPID_URL = "https://mashape-community-urban-dictionary.p.rapidapi.com "
        private const val API_MOCK_VERSION = "beta "
        private const val API_VERSION = "v0 "
        private const val DEFINITION_ENDPOINT = "define?term="
        private const val SETTINGS_MOCK_ENDPOINT = "settings"
    }

    suspend fun fetchSettings(): Resource<BaseSettings> {
        val targetURL = BASE_MOCK_API_URL + API_MOCK_VERSION + SETTINGS_MOCK_ENDPOINT
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

    suspend fun fetchDefinition(term: String): Resource<DefinitionList> {
        val targetURL = BASE_API_URL + API_VERSION + DEFINITION_ENDPOINT + term
        val requestResponse = baseGetRequest(targetURL.buildURL())
        return when (requestResponse.code){
            REQUEST_SUCCEEDED -> {
                val definitionList: DefinitionList = GsonBuilder().create().fromJson(requestResponse.body, DefinitionList::class.java)
                Resource(status = Resource.Status.SUCCESS, data = definitionList, throwable = null)
            }
            else -> Resource(
                status = Resource.Status.ERROR, data = null, throwable = NetworkErrorException(
                    requestResponse.exception
                )
            )
        }
    }

    suspend fun fetchWithRapidDefinition(term: String): Resource<DefinitionList> {
        val targetURL = BASE_RAPID_URL + DEFINITION_ENDPOINT + term
        // In the future, I would retrieve this from Settings downlaoded at the begining of the app, store on Crypto Shared preferences and se here
        val headers = listOf(Pair("x-rapidapi-key","28U3gA79OomshMrQdb3P1heA8FV1p13yqmujsnEtbmcSZq1kiy"), Pair("x-rapidapi-host", "mashape-community-urban-dictionary.p.rapidapi.com"))
        val requestResponse = baseGetWithHeadersRequest(targetURL.buildURL(),headers)
        return when (requestResponse.code){
            REQUEST_SUCCEEDED -> {
                val definitionList: DefinitionList = GsonBuilder().create().fromJson(requestResponse.body, DefinitionList::class.java)
                Resource(status = Resource.Status.SUCCESS, data = definitionList, throwable = null)
            }
            else -> Resource(
                status = Resource.Status.ERROR, data = null, throwable = NetworkErrorException(
                    requestResponse.exception
                )
            )
        }
    }

}