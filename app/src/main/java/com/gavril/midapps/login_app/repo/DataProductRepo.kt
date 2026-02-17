package com.gavril.midapps.login_app.repo

import com.gavril.midapps.login_app.model.DataProduct
import kotlinx.coroutines.flow.Flow

interface DataProductRepo {
    fun getProducts(keyword: String): Flow<List<DataProduct>>
    fun sortProducts(sortBy: String, order:String): Flow<List<DataProduct>>
    fun filterProducts(filter: String): Flow<List<DataProduct>>
    fun pagingProducts(limit: Int, skip: Int): Flow<List<DataProduct>>
}