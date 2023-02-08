package com.tosin.genericproductlist.data.database.datasource

import com.tosin.genericproductlist.AppApplication
import com.tosin.genericproductlist.data.database.entity.ProductLocal
import com.tosin.genericproductlist.data.database.interfaces.ProductDao
import com.tosin.genericproductlist.data.utils.ProviderStaticList

class DoQueriesToLoadProduct : ProductDao {

    override fun fetchProduct(page: Int): List<ProductLocal> {
        return when (page) {
            0 -> ProviderStaticList.getList(
                AppApplication.applicationContext(),
                "product_list.json"
            )
            1 -> ProviderStaticList.getList(
                AppApplication.applicationContext(),
                "product_list_01.json"
            )
            2 -> ProviderStaticList.getList(
                AppApplication.applicationContext(),
                "product_list_02.json"
            )
            3 -> ProviderStaticList.getList(
                AppApplication.applicationContext(),
                "product_list_03.json"
            )
            else -> listOf()
        }
    }
}
