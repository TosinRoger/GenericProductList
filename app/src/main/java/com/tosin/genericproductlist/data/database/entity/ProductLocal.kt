package com.tosin.genericproductlist.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tosin.genericproductlist.domain.factory.ObjectLocal

@Entity(tableName = "product")
data class ProductLocal(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "price")
    val price: Int,
    @ColumnInfo(name = "discountPercentage")
    val discountPercentage: Double,
    @ColumnInfo(name = "rating")
    val rating: Double,
    @ColumnInfo(name = "stock")
    val stock: Int,
    @ColumnInfo(name = "brand")
    val brand: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "thumbnail")
    val thumbnail: String
//    val images: List<String>
) : ObjectLocal
