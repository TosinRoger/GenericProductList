package com.tosin.genericproductlist.domain.data

import androidx.paging.PagingData
import com.tosin.genericproductlist.data.database.interfaces.ProductDao
import com.tosin.genericproductlist.data.model.Product
import com.tosin.genericproductlist.domain.factory.FactoryPagingData
import com.tosin.genericproductlist.domain.paging.source.ProductPagingSource
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val database: ProductDao) {

    fun fetchProductList(): Flow<PagingData<Product>> {
        val pagingSourceFactory = ProductPagingSource(database)
        return FactoryPagingData.fetchList(pagingSourceFactory)
    }
}
