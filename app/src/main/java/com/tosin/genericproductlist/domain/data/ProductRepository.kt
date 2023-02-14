package com.tosin.genericproductlist.domain.data

import androidx.paging.PagingData
import com.tosin.genericproductlist.data.database.interfaces.ProductDao
import com.tosin.genericproductlist.domain.factory.FactoryPagingData
import com.tosin.genericproductlist.domain.model.Product
import com.tosin.genericproductlist.domain.paging.source.ProductPagingSource
import com.tosin.genericproductlist.domain.wrapper.ProductWrapper
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val database: ProductDao) {

    fun fetchProductList(): Flow<PagingData<Product>> {
        val pagingSourceFactory = ProductPagingSource(database)
        return FactoryPagingData.fetchList(pagingSourceFactory)
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
