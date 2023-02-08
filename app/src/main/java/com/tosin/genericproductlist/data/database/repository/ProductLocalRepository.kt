package com.tosin.genericproductlist.data.database.repository

import com.tosin.genericproductlist.AppApplication
import com.tosin.genericproductlist.data.database.entity.ProductLocal
import com.tosin.genericproductlist.data.utils.ProviderStaticList

class ProductLocalRepository {

    fun getList(fileName: String): List<ProductLocal> {
        return ProviderStaticList.getList(AppApplication.applicationContext(), fileName)
    }
}
