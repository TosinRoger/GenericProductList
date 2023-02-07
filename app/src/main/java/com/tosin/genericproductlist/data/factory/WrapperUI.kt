package com.tosin.genericproductlist.data.factory

interface WrapperUI {

    fun toUi(objectLocal: ObjectLocal): ObjectUi
    fun toLocal(objectUi: ObjectUi): ObjectLocal
}
