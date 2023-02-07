package com.tosin.genericproductlist.app.ui.product.list

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tosin.genericproductlist.data.database.datasource.ProductDataSource
import com.tosin.genericproductlist.data.database.source.ProductPagingSource
import com.tosin.genericproductlist.data.model.Product
import kotlinx.coroutines.flow.Flow

class ProductListDataSource {

    fun fetchProductList(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = ProductPagingSource.PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ProductPagingSource(ProductDataSource())
            }
        ).flow
    }
}
