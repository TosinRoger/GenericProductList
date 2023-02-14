package com.tosin.genericproductlist.domain.paging.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tosin.genericproductlist.domain.model.Product

class ProductPagingSource(
    private val listProduct: List<Product>
) : PagingSource<Int, Product>() {
    companion object {
        private const val STARTING_PAGE_INDEX = 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val position = params.key ?: STARTING_PAGE_INDEX

        val nextKey = if (listProduct.isEmpty()) {
            null
        } else {
            position + 1
        }
        return LoadResult.Page(
            data = listProduct,
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
