package com.bhaskar.photobook.interfaces

import com.bhaskar.photobook.constants.Constant.API_END_POINT
import com.bhaskar.photobook.models.ApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET (API_END_POINT)
    suspend fun getAllData(
        @Query ("page") page: Int,
        @Query ("limit") limit: Int
    ): Response<ApiModel>
}