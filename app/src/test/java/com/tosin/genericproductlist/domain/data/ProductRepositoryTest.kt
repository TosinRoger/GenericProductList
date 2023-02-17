package com.tosin.genericproductlist.domain.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tosin.genericproductlist.MainCoroutineRule
import com.tosin.genericproductlist.data.database.entity.ProductLocal
import com.tosin.genericproductlist.data.database.interfaces.ProductDao
import com.tosin.genericproductlist.domain.factory.entity.ProductLocalFactory
import com.tosin.genericproductlist.domain.factory.utils.DataFactory
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
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@RunWith(JUnit4::class)
class ProductRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var database: ProductDao

    private lateinit var repository: ProductRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        repository = ProductRepository(database)
    }

    @AfterEach
    fun clean() {
        clearStaticMockk()
        clearAllMocks()
    }

    @Test
    fun `get all products - empty list`() = runBlocking {
        // GIVEN
        stubProducts(listOf())

        // WHEN
        val productList = repository.getAllProduct()

        // THEN
        assertTrue(productList.isEmpty())
    }

    @Test
    fun `get all products - with list`() = runBlocking {
        // GIVEN
        val productLocal = ProductLocalFactory.makeProductLocal()

        stubProducts(listOf(productLocal))

        // WHEN
        val productList = repository.getAllProduct()

        // THEN
        assertEquals(productLocal.id, productList.first().id)
        assertEquals(productLocal.title, productList.first().title)
        assertEquals(productLocal.description, productList.first().description)
        assertEquals(productLocal.price, productList.first().price)
    }

    @Test
    fun `find product by id`() = runBlocking {
        // GIVEN
        val productLocal = ProductLocalFactory.makeProductLocal()
        stubProduct(productLocal)

        // WHEN
        val responseProduct = repository.findById(productLocal.id)

        // THEN
        assertNotNull(responseProduct)
        assertEquals(productLocal.id, responseProduct.id)
        assertEquals(productLocal.title, responseProduct.title)
        assertEquals(productLocal.description, responseProduct.description)
        assertEquals(productLocal.price, responseProduct.price)
    }

    @Test
    fun `not find product by id`() = runBlocking {
        // GIVEN
        stubProduct(null)

        // WHEN
        val responseProduct = repository.findById(DataFactory.randomInt())

        // THEN
        assertNull(responseProduct)
    }

    private fun stubProducts(productList: List<ProductLocal>) {
        coEvery {
            database.getAllProducts()
        } returns productList
    }

    private fun stubProduct(product: ProductLocal?) {
        coEvery {
            database.findById(any())
        } returns product
    }
}
