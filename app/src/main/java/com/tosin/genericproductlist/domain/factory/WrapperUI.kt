package com.tosin.genericproductlist.domain.factory

interface WrapperUI {

    fun toUi(objectLocal: ObjectLocal): ObjectUi
    fun toLocal(objectUi: ObjectUi): ObjectLocal
}
