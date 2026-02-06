package com.gavril.midapps.login_app.model

import com.google.gson.annotations.SerializedName

data class DataProduct (
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
)