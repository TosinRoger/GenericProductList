package com.tosin.genericproductlist.domain.data

import com.tosin.genericproductlist.data.database.interfaces.ProductDao
import com.tosin.genericproductlist.domain.model.Product
import com.tosin.genericproductlist.domain.wrapper.ProductWrapper
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class ProductRepository(private val database: ProductDao) {

    suspend fun getAllProduct(): List<Product> = withContext(IO) {
        val wrapper = ProductWrapper()
        val churros = database.fetchProduct3()
        val responseProductList = mutableListOf<Product>()

        churros.map { productLocal ->
            val product = wrapper.toUi(productLocal) as Product
            responseProductList.add(product)
        }

        return@withContext responseProductList
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
