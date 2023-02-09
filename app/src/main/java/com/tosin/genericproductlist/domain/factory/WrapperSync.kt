package com.tosin.genericproductlist.domain.factory

interface WrapperSync {

    fun toRemote(objectLocal: ObjectLocal): ObjectRemote
    fun toLocal(objectRemote: ObjectRemote): ObjectLocal
}
