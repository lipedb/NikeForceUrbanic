package com.kforce.urbanic.repository.content

import com.kforce.urbanic.repository.main.IDisposable
import com.kforce.urbanic.repository.main.Resource
import com.kforce.urbanic.repository.settings.BaseSettings
import java.util.function.Consumer

/**
 * Interface for Repository (fetching App Content).
 * */
interface IContent {

    /**
     * Fetches JSON App Settings
     *
     */
    suspend fun fetchAppSettings(): Resource<BaseSettings>
}