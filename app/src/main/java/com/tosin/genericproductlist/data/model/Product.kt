package com.tosin.genericproductlist.data.model

import com.tosin.genericproductlist.domain.factory.ObjectUi

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
