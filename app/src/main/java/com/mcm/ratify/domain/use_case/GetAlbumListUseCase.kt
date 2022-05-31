package com.mcm.ratify.domain.use_case

import com.mcm.ratify.domain.model.Album
import com.mcm.ratify.domain.repository.RatifyRepository
import com.mcm.ratify.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAlbumListUseCase @Inject constructor(
    private val repository: RatifyRepository
) {
    operator fun invoke(
        genreId: Int
    ): Flow<Resource<List<Album>>> = flow {
        emit(Resource.Loading(emptyList()))
        val albumList = repository.getAlbumListByGenre(genreId)
        emit(albumList)
    }
}