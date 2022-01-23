package com.jdsdhp.cinepoliapp.data.repository

import com.jdsdhp.cinepoliapp.data.api.ResponseResult
import com.jdsdhp.cinepoliapp.data.store.model.Profile
import kotlinx.coroutines.flow.Flow

internal interface ProfileRepo {

    val profileFlow: Flow<Profile?>

    suspend fun fetchProfile(): ResponseResult<Nothing>

}