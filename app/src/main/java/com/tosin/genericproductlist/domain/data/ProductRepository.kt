package com.tosin.genericproductlist.domain.data

import androidx.lifecycle.MutableLiveData
import com.tosin.genericproductlist.AppApplication
import com.tosin.genericproductlist.data.database.entity.ProductLocal
import com.tosin.genericproductlist.data.database.interfaces.ProductDao
import com.tosin.genericproductlist.data.utils.ProviderStaticList
import com.tosin.genericproductlist.domain.model.Product
import com.tosin.genericproductlist.domain.wrapper.ProductWrapper
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collectLatest
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

    suspend fun getAllProduct(productList: MutableLiveData<List<Product>>) = withContext(IO) {
        database.getAllProducts().collectLatest {
            val wrapper = ProductWrapper()

            val responseProductList = mutableListOf<Product>()

            it.map { productLocal ->
                val product = wrapper.toUi(productLocal) as Product
                responseProductList.add(product)
            }
            productList.postValue(responseProductList)
        }
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

    fun updateProduct(product: Product) {
        val wrapper = ProductWrapper()
        val productLocal = wrapper.toLocal(product) as ProductLocal
        database.updateProduct(productLocal)
    }
}
