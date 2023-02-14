package com.tosin.genericproductlist.domain.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.tosin.genericproductlist.data.database.interfaces.ProductDao
import com.tosin.genericproductlist.domain.factory.FactoryPagingData
import com.tosin.genericproductlist.domain.model.Product
import com.tosin.genericproductlist.domain.paging.source.ProductPagingSource
import com.tosin.genericproductlist.domain.wrapper.ProductWrapper
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ProductRepository(private val database: ProductDao) {

    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>>
        get() = _productList

    fun fetchProductList(): Flow<PagingData<Product>> {
//        return database.fetchProduct(0)
        val wrapper = ProductWrapper()
        val churros = database.fetchProduct3()
        val responseProductList = mutableListOf<Product>()

        churros.map { productLocal ->
            val product = wrapper.toUi(productLocal) as Product
            responseProductList.add(product)
        }
        _productList.postValue(responseProductList)

        val pagingSourceFactory = ProductPagingSource(responseProductList)
        return FactoryPagingData.fetchList(pagingSourceFactory)

//        val aux = churros.collectLatest { productLocalList ->
//            val productList = productLocalList.map { wrapper.toUi(it) as Product }
//            val pagingSourceFactory = ProductPagingSource(productList)
//            FactoryPagingData.fetchList(pagingSourceFactory)
//        }

//        val pagingSourceFactory = ProductPagingSource(database)
//        return FactoryPagingData.fetchList(pagingSourceFactory)
    }

    suspend fun getAllProduct(): List<Product> = withContext(IO) {
        val wrapper = ProductWrapper()
        val churros = database.fetchProduct3()
        val responseProductList = mutableListOf<Product>()

        churros.map { productLocal ->
            val product = wrapper.toUi(productLocal) as Product
            responseProductList.add(product)
        }
        _productList.postValue(responseProductList)

        return@withContext responseProductList
    }

    fun findById(productId: Int): Product? {
        val productLocal = database.findById(productId)

        return if (productLocal != null) {
            val wrapper = ProductWrapper()
            wrapper.toUi(productLocal) as Product
        } else {
            null
        }
    }
}
