package com.tosin.genericproductlist.domain.factory.entity

import com.tosin.genericproductlist.domain.factory.utils.DataFactory
import com.tosin.genericproductlist.domain.model.Product

object ProductFactory {

    fun makeProduct(): Product {
        return Product(
            DataFactory.randomInt(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomInt(),
            DataFactory.randomDouble(),
            DataFactory.randomDouble(),
            DataFactory.randomInt(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            listOf(DataFactory.randomString())
        )
    }
}
