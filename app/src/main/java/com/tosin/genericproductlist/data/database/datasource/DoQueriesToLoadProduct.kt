package com.tosin.genericproductlist.data.database.datasource

import com.tosin.genericproductlist.AppApplication
import com.tosin.genericproductlist.data.database.entity.ProductLocal
import com.tosin.genericproductlist.data.utils.ProviderStaticList

class DoQueriesToLoadProduct {

    companion object {
        const val PAGE_ONE = 0
        private const val PAGE_TWO = 1
        private const val PAGE_THREE = 2
        const val PAGE_FOUR = 3
    }

    fun fetchProduct(page: Int): List<ProductLocal> {
        return when (page) {
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
    }

    fun findById(productId: Int): ProductLocal? {
        val products = mutableListOf<ProductLocal>()
        for (index in PAGE_ONE..PAGE_FOUR) {
            val aux = fetchProduct(index)
            products.addAll(aux)
        }

        var product: ProductLocal? = null

        products.forEach { productLocal ->
            if (productId == productLocal.id) {
                product = productLocal
            }
        }

        return product
    }
}
