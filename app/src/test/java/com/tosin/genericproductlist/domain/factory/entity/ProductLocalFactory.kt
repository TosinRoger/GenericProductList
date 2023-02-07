package com.tosin.genericproductlist.domain.factory.entity

import com.tosin.genericproductlist.data.database.entity.ProductLocal
import com.tosin.genericproductlist.domain.factory.utils.DataFactory

object ProductLocalFactory {

    fun makeProductLocal(): ProductLocal {
        return ProductLocal(
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