package com.tosin.genericproductlist.app.ui.product.list

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.tosin.genericproductlist.app.ui.UiProductState
import com.tosin.genericproductlist.app.ui.UiState
import com.tosin.genericproductlist.data.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update

class ProductListViewModel(private val dataSource: ProductListDataSource) : ViewModel() {
    private val uiScope = CoroutineScope(Job() + IO)

    private val _uiState = MutableStateFlow(UiProductState(UiState.EMPTY_LIST))
    val uiState = _uiState.asStateFlow()

    override fun onCleared() {
        super.onCleared()
        uiScope.cancel()
    }

    fun loadProducts(): Flow<PagingData<Product>> {
        _uiState.update {
            it.copy(uiState = UiState.LOADING)
        }
        val aux = dataSource.fetchProductList()

        _uiState.update {
            it.copy(uiState = UiState.DONE)
        }

        return aux
    }
}
