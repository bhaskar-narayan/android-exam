package com.bhaskar.photobook.interfaces

import com.bhaskar.photobook.constants.Constant.API_END_POINT
import com.bhaskar.photobook.models.ApiModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET (API_END_POINT)
    fun getAllData(
        @Query ("page") page: Int,
        @Query ("limit") limit: Int
    ): Call<ApiModel>
}