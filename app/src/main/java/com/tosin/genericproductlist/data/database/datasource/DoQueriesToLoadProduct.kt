package com.tosin.genericproductlist.data.database.datasource

import com.tosin.genericproductlist.AppApplication
import com.tosin.genericproductlist.data.database.entity.ProductLocal
import com.tosin.genericproductlist.data.database.interfaces.ProductDao
import com.tosin.genericproductlist.data.utils.ProviderStaticList

class DoQueriesToLoadProduct : ProductDao {

    companion object {
        private const val PAGE_ONE = 0
        private const val PAGE_TWO = 1
        private const val PAGE_THREE = 2
        private const val PAGE_FOUR = 3
    }

    override fun fetchProduct(page: Int): List<ProductLocal> {
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
}
