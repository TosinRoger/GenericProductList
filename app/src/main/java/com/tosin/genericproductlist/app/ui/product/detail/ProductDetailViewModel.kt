package com.tosin.genericproductlist.app.ui.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tosin.genericproductlist.domain.data.ProductRepository
import com.tosin.genericproductlist.domain.model.Product
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class ProductDetailViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

    suspend fun loadProductById(productId: Int) = withContext(IO) {
        repository.findById(productId)?.let {
            _product.postValue(it)
        }
    }
}
