package com.gavril.midapps.login_app.api

import com.gavril.midapps.login_app.api.response.AuthResponse
import com.gavril.midapps.login_app.api.response.ProductResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
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
    @GET("products/search")
    suspend fun sortProducts(
        @Query("sortBy") sortBy: String,
        @Query("order") order: String
        ): ProductResponse
    @GET("products/category/{category}")
    suspend fun filterProducts(
        @Path("category") category: String
    ): ProductResponse
    @GET("products")
    suspend fun pagingProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
    ): ProductResponse
}