package com.tosin.genericproductlist.app.ui.product.list

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.tosin.genericproductlist.data.database.datasource.ProductDataSource
import com.tosin.genericproductlist.data.database.entity.ProductLocal
import com.tosin.genericproductlist.data.database.source.ProductPagingSource
import com.tosin.genericproductlist.domain.factory.entity.ProductLocalFactory
import com.tosin.genericproductlist.domain.wrapper.ProductWrapper
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.clearStaticMockk
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProductListViewModelTest {

    private lateinit var viewModel: ProductListViewModel
    private lateinit var productPagingSource: ProductPagingSource

    @MockK
    private lateinit var dataSource: ProductListDataSource

    @MockK
    private lateinit var pagingDataSource: ProductDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = ProductListViewModel(dataSource)
        productPagingSource = ProductPagingSource(pagingDataSource)
    }

    @AfterEach
    fun clean() {
        clearStaticMockk()
        clearAllMocks()
    }

    @Test
    fun `load empty list`() = runBlocking {
        // GIVEN
        val responseList = listOf<ProductLocal>()

        stubPagingList(responseList)

        // WHEN
        viewModel.loadProducts()

        // THEN
        val expected = PagingSource.LoadResult.Page(
            data = responseList,
            prevKey = null,
            nextKey = null
        )

        assertEquals(
            productPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            ),
            expected
        )
    }

//    @Test
//    fun `load list with some product`() = runBlocking {
//        // GIVEN
//        val responseList = listOf(ProductLocalFactory.makeProductLocal())
//
//        val wrapper = ProductWrapper()
//        val responseProduct = wrapper.toUi(responseList.first())
//        val responseListExpected = listOf(responseProduct)
//
//        stubPagingList(responseList)
//
//        // WHEN
//        viewModel.loadProducts()
//
//        // THEN
//        val expected = PagingSource.LoadResult.Page(
//            data = responseListExpected,
//            prevKey = null,
//            nextKey = null
//        )
//
//        assertEquals(
//            productPagingSource.load(
//                PagingSource.LoadParams.Refresh(
//                    key = null,
//                    loadSize = 2,
//                    placeholdersEnabled = false
//                )
//            ),
//            expected
//        )
//    }

    private fun stubPagingList(responseList: List<ProductLocal>) {
        coEvery {
            pagingDataSource.fetchProduct(0)
        } returns responseList

        val pager = Pager(
            config = PagingConfig(pageSize = 1, enablePlaceholders = false),
            pagingSourceFactory = { productPagingSource }
        )

        coEvery {
            dataSource.fetchProductList()
        } returns pager.flow
    }
}
