package com.rx.rxretroapp.retrorx.repository

import io.reactivex.Single
import retrofit2.http.GET
import com.rx.rxretroapp.retrorx.model.RetroRxModel
import retrofit2.Response
import retrofit2.adapter.rxjava2.Result

interface APIService {

    @GET("posts")
    fun makeRequest(): Single<List<RetroRxModel>>

    @GET("posts")
    suspend fun  fetchPosts(): Response<List<RetroRxModel>>



}
