package com.tosin.genericproductlist.data.remote.entity

import com.tosin.genericproductlist.domain.factory.ObjectRemote

data class ProductRemote(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>
) : ObjectRemote()
