package com.tosin.genericproductlist.data.models

import com.tosin.genericproductlist.data.factory.ObjectUi

data class Product(
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
) : ObjectUi()
