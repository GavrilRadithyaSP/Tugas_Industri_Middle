package com.gavril.midapps.login_app.api

import com.gavril.midapps.login_app.api.response.AuthResponse
import com.gavril.midapps.login_app.api.response.ProductResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): AuthResponse
    @GET("products/search")
    suspend fun getProducts(
        @Query("q") keyword: String
    ): ProductResponse
}