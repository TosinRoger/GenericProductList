package com.tosin.genericproductlist.data.database.datasource

import com.tosin.genericproductlist.data.database.entity.ProductLocal
import com.tosin.genericproductlist.data.database.interfaces.ProductDao
import com.tosin.genericproductlist.data.database.repository.ProductLocalRepository

class ProductDataSource : ProductDao {

    override fun fetchProduct(page: Int): List<ProductLocal> {
        return if (page < 1) {
            ProductLocalRepository().getList()
        } else {
            listOf()
        }
    }
}
