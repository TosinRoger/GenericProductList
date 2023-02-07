package com.tosin.genericproductlist.data.factory

interface WrapperSync {

    fun toRemote(objectLocal: ObjectLocal): ObjectRemote
    fun toLocal(objectRemote: ObjectRemote): ObjectLocal
}
