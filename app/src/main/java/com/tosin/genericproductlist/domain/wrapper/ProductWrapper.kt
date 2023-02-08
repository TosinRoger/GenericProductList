package com.tosin.genericproductlist.domain.wrapper

import com.tosin.genericproductlist.data.database.entity.ProductLocal
import com.tosin.genericproductlist.domain.model.Product
import com.tosin.genericproductlist.data.remote.entity.ProductRemote
import com.tosin.genericproductlist.domain.factory.ObjectLocal
import com.tosin.genericproductlist.domain.factory.ObjectRemote
import com.tosin.genericproductlist.domain.factory.ObjectUi
import com.tosin.genericproductlist.domain.factory.WrapperSync
import com.tosin.genericproductlist.domain.factory.WrapperUI

class ProductWrapper : WrapperUI, WrapperSync {

    override fun toUi(objectLocal: ObjectLocal): ObjectUi {
        val productLocal = objectLocal as ProductLocal
        return Product(
            productLocal.id,
            productLocal.title,
            productLocal.description,
            productLocal.price,
            productLocal.discountPercentage,
            productLocal.rating,
            productLocal.stock,
            productLocal.brand,
            productLocal.category,
            productLocal.thumbnail,
            productLocal.images
        )
    }

    override fun toLocal(objectUi: ObjectUi): ObjectLocal {
        val productUi = objectUi as Product
        return ProductLocal(
            productUi.id,
            productUi.title,
            productUi.description,
            productUi.price,
            productUi.discountPercentage,
            productUi.rating,
            productUi.stock,
            productUi.brand,
            productUi.category,
            productUi.thumbnail,
            productUi.images
        )
    }

    override fun toRemote(objectLocal: ObjectLocal): ObjectRemote {
        val productLocal = objectLocal as ProductLocal
        return ProductRemote(
            productLocal.id,
            productLocal.title,
            productLocal.description,
            productLocal.price,
            productLocal.discountPercentage,
            productLocal.rating,
            productLocal.stock,
            productLocal.brand,
            productLocal.category,
            productLocal.thumbnail,
            productLocal.images
        )
    }

    override fun toLocal(objectRemote: ObjectRemote): ObjectLocal {
        val productRemote = objectRemote as ProductRemote
        return ProductLocal(
            productRemote.id,
            productRemote.title,
            productRemote.description,
            productRemote.price,
            productRemote.discountPercentage,
            productRemote.rating,
            productRemote.stock,
            productRemote.brand,
            productRemote.category,
            productRemote.thumbnail,
            productRemote.images
        )
    }
}
