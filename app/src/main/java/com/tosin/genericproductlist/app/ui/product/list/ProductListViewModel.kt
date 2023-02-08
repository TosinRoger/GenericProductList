package com.tosin.genericproductlist.app.ui.product.list

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.tosin.genericproductlist.app.ui.state.UiProductState
import com.tosin.genericproductlist.app.ui.state.UiState
import com.tosin.genericproductlist.domain.model.Product
import com.tosin.genericproductlist.domain.data.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductListViewModel(private val repository: ProductRepository) : ViewModel() {
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
        val aux = repository.fetchProductList()

        _uiState.update {
            it.copy(uiState = UiState.DONE)
        }

        return aux
    }
}
