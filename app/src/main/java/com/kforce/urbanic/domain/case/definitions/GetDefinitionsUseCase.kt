package com.kforce.urbanic.domain.case.definitions

import com.kforce.urbanic.domain.case.core.BaseUseCase
import com.kforce.urbanic.domain.exception.Failure
import com.kforce.urbanic.extension.notNull
import com.kforce.urbanic.repository.content.ContentImplementation
import com.kforce.urbanic.repository.core.Resource
import com.kforce.urbanic.repository.definition.DefinitionList
import com.kforce.urbanic.service.content.ContentService
import com.kforce.urbanic.util.EMPTY_STRING
import com.kforce.urbanic.util.Either
import com.kforce.urbanic.util.SEARCH_ERROR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetDefinitionsUseCase(
    private val service: ContentService,
    private val term: String
) : BaseUseCase<DefinitionList, Any?>() {

    override suspend fun run(params: Any?): Either<Failure, DefinitionList> {
        var definitionList: Resource<DefinitionList>?
        try {
            definitionList = withContext(Dispatchers.IO) {ContentImplementation(service).fetchDefinition(term)}
        } catch (exp: Exception) {
            return Either.Fail(GetDefinitionsUseCase(exp))
        }
        definitionList.notNull {
            return when (this.status) {
                Resource.Status.SUCCESS -> {
                    when {
                        data != null && data.list.isNotEmpty()-> {
                            Either.Success(data)
                        }
                        else -> {
                            Either.Fail(GetDefinitionsUseCase(Exception(SEARCH_ERROR)))
                        }
                    }
                }
                else -> Either.Fail(GetDefinitionsUseCase(Exception(this.throwable)))
            }
        }
        return Either.Fail(GetDefinitionsUseCase(Exception(EMPTY_STRING)))
    }

    data class GetDefinitionsUseCase(val error: Exception) : Failure.FeatureFailure(error)
}