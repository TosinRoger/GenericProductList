package com.tosin.genericproductlist.domain.paging.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tosin.genericproductlist.data.database.interfaces.ProductDao
import com.tosin.genericproductlist.data.model.Product
import com.tosin.genericproductlist.domain.wrapper.ProductWrapper

class ProductPagingSource(
    private val productLocalRepo: ProductDao
) : PagingSource<Int, Product>() {
    companion object {
        private const val STARTING_PAGE_INDEX = 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val position = params.key ?: STARTING_PAGE_INDEX
        val wrapper = ProductWrapper()
        val response = productLocalRepo.fetchProduct(
            page = position
        ).map { wrapper.toUi(it) as Product }
        val nextKey = if (response.isEmpty()) {
            null
        } else {
            position + 1
        }
        return LoadResult.Page(
            data = response,
            prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
            nextKey = nextKey
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
