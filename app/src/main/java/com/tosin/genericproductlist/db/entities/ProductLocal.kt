package com.tosin.genericproductlist.db.entities

import com.tosin.genericproductlist.data.factory.ObjectLocal

data class ProductLocal(
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
) : ObjectLocal()
