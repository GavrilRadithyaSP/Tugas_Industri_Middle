package com.gavril.midapps.login_app.repo

import com.gavril.midapps.login_app.model.DataProduct
import kotlinx.coroutines.flow.Flow

interface DataProductRepo {
    fun getProducts(keyword: String): Flow<List<DataProduct>>
}