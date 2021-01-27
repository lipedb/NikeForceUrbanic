package com.kforce.urbanic.repository.content

import com.kforce.urbanic.repository.core.Resource
import com.kforce.urbanic.repository.definition.DefinitionList
import com.kforce.urbanic.repository.settings.BaseSettings
import com.kforce.urbanic.service.content.ContentService

class ContentImplementation(
    private val contentService: ContentService
): IContent {

    override suspend fun fetchAppSettings(
    ): Resource<BaseSettings> {
        val result = contentService.fetchSettings()
        return when (result.status) {
            Resource.Status.SUCCESS -> {
                Resource.success(result.data)
            }
            else -> Resource(Resource.Status.ERROR, null, result.throwable)
        }
    }

    override suspend fun fetchDefinition(
        term: String,
        withRapid: Boolean
    ): Resource<DefinitionList> {
        val result = when {
            withRapid -> { contentService.fetchWithRapidDefinition(term) }
            else -> { contentService.fetchDefinition(term) }
        }
        return when (result.status) {
            Resource.Status.SUCCESS -> {
                Resource.success(result.data)
            }
            else -> Resource(Resource.Status.ERROR, null, result.throwable)
        }
    }
}