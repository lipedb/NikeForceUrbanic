package com.kforce.urbanic.repository.content

import com.kforce.urbanic.repository.main.IDisposable
import com.kforce.urbanic.repository.main.Resource
import com.kforce.urbanic.repository.settings.BaseSettings
import com.kforce.urbanic.service.content.ContentService
import java.util.function.Consumer

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
}