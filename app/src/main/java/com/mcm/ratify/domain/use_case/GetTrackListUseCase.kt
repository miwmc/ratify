package com.mcm.ratify.domain.use_case

import com.mcm.ratify.domain.model.Track
import com.mcm.ratify.domain.repository.RatifyRepository
import com.mcm.ratify.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTrackListUseCase @Inject constructor(
    private val repository: RatifyRepository
) {
    operator fun invoke(
        albumId: Long
    ): Flow<Resource<List<Track>>> = flow {
        emit(Resource.Loading(emptyList()))
        val trackList = repository.getTrackListByAlbum(albumId)
        emit(trackList)
    }
}