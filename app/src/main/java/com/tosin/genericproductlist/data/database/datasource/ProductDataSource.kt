package com.tosin.genericproductlist.data.database.datasource

import com.tosin.genericproductlist.data.database.entity.ProductLocal
import com.tosin.genericproductlist.data.database.interfaces.ProductDao
import com.tosin.genericproductlist.data.database.repository.ProductLocalRepository

class ProductDataSource : ProductDao {

    override fun fetchProduct(page: Int): List<ProductLocal> {
        return when (page) {
            0 -> ProductLocalRepository().getList("product_list.json")
            1 -> ProductLocalRepository().getList("product_list_01.json")
            2 -> ProductLocalRepository().getList("product_list_02.json")
            3 -> ProductLocalRepository().getList("product_list_03.json")
            else -> listOf()
        }
    }
}
