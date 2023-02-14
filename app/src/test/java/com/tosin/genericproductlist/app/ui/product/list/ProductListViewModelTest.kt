package com.tosin.genericproductlist.app.ui.product.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tosin.genericproductlist.MainCoroutineRule
import com.tosin.genericproductlist.domain.data.ProductRepository
import com.tosin.genericproductlist.domain.factory.entity.ProductFactory
import com.tosin.genericproductlist.domain.model.Product
import com.tosin.genericproductlist.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.clearStaticMockk
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProductListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ProductListViewModel

    @MockK
    private lateinit var repository: ProductRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = ProductListViewModel(repository)
    }

    @AfterEach
    fun clean() {
        clearStaticMockk()
        clearAllMocks()
    }

    @Test
    fun `load empty list`() = runBlocking {
        // GIVEN
        val responseList = listOf<Product>()

        stubPagingList(responseList)

        // WHEN
        viewModel.loadList()

        // THEN
        val response = viewModel.productList.getOrAwaitValue()

        assertEquals(responseList, response)
    }

    @Test
    fun `load list with some product`() = runBlocking {
        // GIVEN
        val product = ProductFactory.makeProduct()
        val responseList = listOf(product)

        stubPagingList(responseList)

        // WHEN
        viewModel.loadList()

        // THEN
        val response = viewModel.productList.getOrAwaitValue()
        assertEquals(responseList, response)
    }

    private fun stubPagingList(responseList: List<Product>) {
        coEvery {
            repository.getAllProduct()
        } returns responseList
    }
}
