package com.tosin.genericproductlist.data.database.repository

import com.tosin.genericproductlist.AppApplication
import com.tosin.genericproductlist.data.database.entity.ProductLocal
import com.tosin.genericproductlist.data.utils.ProviderStaticList

class ProductLocalRepository {

    fun getList(): List<ProductLocal> {
        return ProviderStaticList.getList(AppApplication.applicationContext())
    }
}
