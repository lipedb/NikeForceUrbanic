@file:Suppress("DEPRECATION")

package com.kforce.urbanic.service.main

import com.kforce.urbanic.repository.main.RemoteResponse
import com.kforce.urbanic.repository.main.Resource
import com.kforce.urbanic.util.EMPTY_STRING
import com.kforce.urbanic.util.RESPONSE_ERROR
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils
import java.io.IOException


suspend fun baseGetRequest(requestURL: String): RemoteResponse {
    val mRequest = HttpGet(requestURL)
    val client = DefaultHttpClient()
     var code: Int
     var exception: String
     var body: String
     try {
        val response = client.execute(mRequest)
        code = response.statusLine.statusCode
        exception =  response.statusLine.reasonPhrase
        val entity = response.entity
         body = EntityUtils.toString(entity, "UTF-8")
    } catch (e: IOException) {
        mRequest.abort()
         code = 4
         exception = RESPONSE_ERROR
         body = EMPTY_STRING
    }
    return RemoteResponse(code, body, exception)
}
