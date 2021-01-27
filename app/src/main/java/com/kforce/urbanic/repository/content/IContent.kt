package com.kforce.urbanic.repository.content

import com.kforce.urbanic.repository.core.Resource
import com.kforce.urbanic.repository.definition.DefinitionList
import com.kforce.urbanic.repository.settings.BaseSettings

/**
 * Interface for Repository (fetching App Content).
 * */
interface IContent {

    /**
     * Fetches JSON App Settings
     *
     */
    suspend fun fetchAppSettings(): Resource<BaseSettings>

    /**
     * Fetches Definition for word
     *
     */
    suspend fun fetchDefinition(term: String, withRapid: Boolean = false): Resource<DefinitionList>
}