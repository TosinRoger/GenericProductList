package com.tosin.genericproductlist.data.database.interfaces

import com.tosin.genericproductlist.data.database.entity.ProductLocal

interface ProductDao {

    fun fetchProduct(page: Int): List<ProductLocal>
}
