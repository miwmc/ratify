package com.mcm.ratify.domain.use_case

import com.mcm.ratify.domain.model.Genre
import com.mcm.ratify.domain.repository.RatifyRepository
import com.mcm.ratify.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGenreListUseCase @Inject constructor(
    private val repository: RatifyRepository
) {
    operator fun invoke(): Flow<Resource<List<Genre>>> = flow {
        emit(Resource.Loading(emptyList()))
        val genreList = repository.getGenreList()
        emit(genreList)
    }
}