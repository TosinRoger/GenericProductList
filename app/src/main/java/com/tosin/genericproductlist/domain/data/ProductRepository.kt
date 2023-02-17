package com.tosin.genericproductlist.domain.data

import com.tosin.genericproductlist.AppApplication
import com.tosin.genericproductlist.data.database.entity.ProductLocal
import com.tosin.genericproductlist.data.database.interfaces.ProductDao
import com.tosin.genericproductlist.data.utils.ProviderStaticList
import com.tosin.genericproductlist.domain.model.Product
import com.tosin.genericproductlist.domain.wrapper.ProductWrapper
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class ProductRepository(private val database: ProductDao) {

    companion object {
        private const val PAGE_ONE = 0
        private const val PAGE_TWO = 1
        private const val PAGE_THREE = 2
        private const val PAGE_FOUR = 3
    }

    fun fetchProductFromJson(): List<ProductLocal> {
        val productLocalList = mutableListOf<ProductLocal>()
        for (index in PAGE_ONE..PAGE_FOUR) {
            val list = when (index) {
                PAGE_ONE -> ProviderStaticList.getList(
                    AppApplication.applicationContext(),
                    "product_list.json"
                )
                PAGE_TWO -> ProviderStaticList.getList(
                    AppApplication.applicationContext(),
                    "product_list_01.json"
                )
                PAGE_THREE -> ProviderStaticList.getList(
                    AppApplication.applicationContext(),
                    "product_list_02.json"
                )
                PAGE_FOUR -> ProviderStaticList.getList(
                    AppApplication.applicationContext(),
                    "product_list_03.json"
                )
                else -> listOf()
            }
            productLocalList.addAll(list)
        }
        return productLocalList
    }

    suspend fun getAllProduct(): List<Product> = withContext(IO) {
        val wrapper = ProductWrapper()
        val productLocalList = database.getAllProducts()
        val responseProductList = mutableListOf<Product>()

        productLocalList.map { productLocal ->
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
