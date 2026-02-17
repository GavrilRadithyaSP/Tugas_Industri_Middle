package com.gavril.midapps.login_app.ui.home

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.crocodic.core.base.adapter.CorePagingSource
import com.crocodic.core.data.CoreSession
import com.gavril.midapps.friend_app.base.BaseViewModel
import com.gavril.midapps.login_app.model.DataProduct
import com.gavril.midapps.login_app.repo.DataProductRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repoDataProduct: DataProductRepo, private val coreSession: CoreSession): BaseViewModel() {
    val queries = MutableStateFlow<Triple<String?, String?, String?>>(Triple(null, null, null))
    private val incPage = 10
    fun getPagingProducts(): Flow<PagingData<DataProduct>> {
        return queries.flatMapLatest {
            Pager(
                config = CorePagingSource.config(incPage),
                pagingSourceFactory = {
                    CorePagingSource(0) { _, limit: Int ->
                        repoDataProduct.pagingProducts(limit, countPage()).first()
                    }
                }
            ).flow.cachedIn(viewModelScope)
        }
    }
    private fun countPage(): Int{
        val page = coreSession.getInt("page")
        coreSession.setValue("page" ,page + incPage)
        return page
    }
    private val _product = MutableSharedFlow<List<DataProduct>>()
    val product = _product.asSharedFlow()
    fun getProduct(keyword: String = "") = viewModelScope.launch {
        repoDataProduct.getProducts(keyword).collect {
            _product.emit(it)
        }
    }
    fun sortProduct(sortBy: String = "", order: String = "") = viewModelScope.launch {
        repoDataProduct.sortProducts(sortBy, order).collect {
            _product.emit(it)
        }
    }
    fun filterProduct(filter: String = "") = viewModelScope.launch {
        repoDataProduct.filterProducts(filter).collect {
            _product.emit(it)
        }
    }
}