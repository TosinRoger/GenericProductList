package com.tosin.genericproductlist.app.ui.product.detail

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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ProductDetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ProductDetailViewModel

    @MockK
    private lateinit var repository: ProductRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = ProductDetailViewModel(repository)
    }

    @AfterEach
    fun clean() {
        clearStaticMockk()
        clearAllMocks()
    }

    @Test
    fun `load product by ID`() = runBlocking {
        // GIVEN
        val product = ProductFactory.makeProduct()

        stubProduct(product)

        // WHEN
        viewModel.loadProductById(product.id)

        // THEN
        val response = viewModel.product.getOrAwaitValue()

        assertEquals(product, response)
    }

    private fun stubProduct(product: Product) {
        coEvery {
            repository.findById(any())
        } returns product
    }
}
