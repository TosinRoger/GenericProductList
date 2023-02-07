package com.tosin.genericproductlist.data.wrapper

import com.tosin.genericproductlist.data.factory.ObjectLocal
import com.tosin.genericproductlist.data.factory.ObjectRemote
import com.tosin.genericproductlist.data.factory.ObjectUi
import com.tosin.genericproductlist.data.factory.WrapperSync
import com.tosin.genericproductlist.data.factory.WrapperUI
import com.tosin.genericproductlist.data.models.Product
import com.tosin.genericproductlist.db.entities.ProductLocal
import com.tosin.genericproductlist.remote.entity.ProductRemote

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
