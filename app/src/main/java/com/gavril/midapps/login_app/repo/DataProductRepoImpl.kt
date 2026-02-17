package com.gavril.midapps.login_app.repo

import android.util.Log
import com.crocodic.core.api.ApiObserver
import com.gavril.midapps.login_app.api.ApiService
import com.gavril.midapps.login_app.api.response.ProductResponse
import com.gavril.midapps.login_app.model.DataProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataProductRepoImpl @Inject constructor(private val apiService: ApiService): DataProductRepo {
    override fun getProducts(keyword: String): Flow<List<DataProduct>> = flow {
        ApiObserver.run({apiService.getProducts(keyword)},
            false,
            object : ApiObserver.ModelResponseListener<ProductResponse>{
                override suspend fun onSuccess(response: ProductResponse) {
                    Log.d("API","Product Response: ${response.products}")
                    emit(response.products)
                }
                override suspend fun onError(response: ProductResponse) {
                    emit(emptyList())
                }
            })
    }
    override fun sortProducts(
        sortBy: String,
        order: String
    ): Flow<List<DataProduct>> = flow {
        ApiObserver.run({apiService.sortProducts(sortBy, order)},
            false,
            object : ApiObserver.ModelResponseListener<ProductResponse>{
                override suspend fun onSuccess(response: ProductResponse) {
                    /*Log.d("API","Product Response: ${response.products}")*/
                    emit(response.products)
                }
                override suspend fun onError(response: ProductResponse) {
                    emit(emptyList())
                }
            })
    }
    override fun filterProducts(filter: String): Flow<List<DataProduct>> = flow {
        ApiObserver.run({apiService.filterProducts(filter)},
            false,
            object : ApiObserver.ModelResponseListener<ProductResponse>{
                override suspend fun onSuccess(response: ProductResponse) {
                    /*Log.d("API","Product Response: ${response.products}")*/
                    emit(response.products)
                }
                override suspend fun onError(response: ProductResponse) {
                    emit(emptyList())
                }
            })
    }
    override fun pagingProducts(limit: Int, skip: Int): Flow<List<DataProduct>> {
        return flow {
            val response = apiService.pagingProducts(limit, skip)
            emit(response.products)
        }
    }
}