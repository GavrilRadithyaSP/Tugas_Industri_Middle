package com.gavril.midapps.login_app.api.response

import com.crocodic.core.api.ModelResponse
import com.gavril.midapps.login_app.model.DataProduct
import com.google.gson.annotations.SerializedName

data class ProductResponse (
    @SerializedName("products")
    val products: List<DataProduct>
): ModelResponse()