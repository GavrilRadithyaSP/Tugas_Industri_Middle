package com.gavril.midapps.login_app.api.response

import com.crocodic.core.api.ModelResponse
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("accessToken")
    val token: String?,
    @SerializedName("id")
    var id: Int,
    @SerializedName("username")
    var username: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("firstName")
    var firstName: String,
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("gender")
    var gender: String,
    @SerializedName("image")
    var image: String
): ModelResponse()