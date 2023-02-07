package com.tosin.genericproductlist.app.ui.product.list

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.tosin.genericproductlist.data.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow

class ProductListViewModel(private val dataSource: ProductListDataSource) : ViewModel() {
    private val uiScope = CoroutineScope(Job() + IO)

    override fun onCleared() {
        super.onCleared()
        uiScope.cancel()
    }

    fun loadProducts(): Flow<PagingData<Product>> {
        return dataSource.fetchProductList()
    }
}
