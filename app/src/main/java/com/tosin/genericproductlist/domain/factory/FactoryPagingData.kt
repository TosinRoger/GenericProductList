package com.tosin.genericproductlist.domain.factory

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

object FactoryPagingData {

    private const val PAGE_SIZE = 10

    fun <T : Any> fetchList(pagingSourceFactory: PagingSource<Int, T>): Flow<PagingData<T>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                pagingSourceFactory
            }
        ).flow
    }
}
