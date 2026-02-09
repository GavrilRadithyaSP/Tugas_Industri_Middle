package com.gavril.midapps.login_app.ui.home

import androidx.lifecycle.viewModelScope
import com.gavril.midapps.friend_app.base.BaseViewModel
import com.gavril.midapps.login_app.model.DataProduct
import com.gavril.midapps.login_app.repo.DataProductRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repoDataProduct: DataProductRepo): BaseViewModel() {
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