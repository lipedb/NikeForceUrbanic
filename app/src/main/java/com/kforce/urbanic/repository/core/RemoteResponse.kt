package com.kforce.urbanic.repository.core

/**
 * A class that holds the service response.
 * @param <T>
 */
data class RemoteResponse(val code: Int, val body: String, val exception: String)