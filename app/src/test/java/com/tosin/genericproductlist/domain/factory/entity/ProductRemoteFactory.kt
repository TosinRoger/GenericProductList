package com.tosin.genericproductlist.domain.factory.entity

import com.tosin.genericproductlist.data.remote.entity.ProductRemote
import com.tosin.genericproductlist.domain.factory.utils.DataFactory

object ProductRemoteFactory {

    fun makeProductRemote(): ProductRemote {
        return ProductRemote(
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
