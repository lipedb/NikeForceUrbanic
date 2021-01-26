package com.kforce.urbanic.domain.case.settings

import com.kforce.urbanic.domain.case.core.BaseUseCase
import com.kforce.urbanic.domain.exception.Failure
import com.kforce.urbanic.extension.notNull
import com.kforce.urbanic.repository.content.ContentImplementation
import com.kforce.urbanic.repository.main.Resource
import com.kforce.urbanic.repository.settings.BaseSettings
import com.kforce.urbanic.service.content.ContentService
import com.kforce.urbanic.util.EMPTY_STRING
import com.kforce.urbanic.util.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetSettingsUseCase(
    private val service: ContentService
) : BaseUseCase<BaseSettings, Any?>() {

    override suspend fun run(params: Any?): Either<Failure, BaseSettings> {
        var baseSettings: Resource<BaseSettings>?
        try {
            baseSettings = withContext(Dispatchers.IO) {ContentImplementation(service).fetchAppSettings()}
        } catch (exp: Exception) {
            return Either.Fail(GetSettingsUseCase(exp))
        }
        baseSettings.notNull {
            return when (this.status) {
                Resource.Status.SUCCESS -> {
                    when {
                        data != null -> {
                            Either.Success(data)
                        }
                        else -> {
                            Either.Fail(GetSettingsUseCase(Exception(EMPTY_STRING)))
                        }
                    }
                }
                else -> Either.Fail(GetSettingsUseCase(Exception(this.throwable)))
            }
        }
        return Either.Fail(GetSettingsUseCase(Exception(EMPTY_STRING)))
    }

    data class GetSettingsUseCase(val error: Exception) : Failure.FeatureFailure(error)
}