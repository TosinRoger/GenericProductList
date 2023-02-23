package com.tosin.genericproductlist.data.database.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tosin.genericproductlist.data.database.entity.ProductLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun fetchProductFromJson(): Flow<List<ProductLocal>>

    @Query("SELECT * FROM product")
    fun getAllProducts(): Flow<List<ProductLocal>>

    @Insert
    fun insertProduct(productLocal: ProductLocal)

    @Query("SELECT * FROM product WHERE id = :productId")
    fun findById(productId: Int): ProductLocal?

    @Update
    fun updateProduct(productLocal: ProductLocal)
}
