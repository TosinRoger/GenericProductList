package com.tosin.genericproductlist.data.database.interfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tosin.genericproductlist.data.database.entity.ProductLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun fetchProduct(): Flow<List<ProductLocal>>

    @Query("SELECT * FROM product")
    fun fetchProduct2(): LiveData<List<ProductLocal>>

    @Query("SELECT * FROM product")
    fun fetchProduct3(): List<ProductLocal>

    @Insert
    fun insertProduct(productLocal: ProductLocal)

    @Query("SELECT * FROM product WHERE id = :productId")
    fun findById(productId: Int): ProductLocal?
}
