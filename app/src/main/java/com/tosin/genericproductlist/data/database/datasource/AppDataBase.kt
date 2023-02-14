package com.tosin.genericproductlist.data.database.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tosin.genericproductlist.data.database.entity.ProductLocal
import com.tosin.genericproductlist.data.database.interfaces.ProductDao

@Database(version = 1, entities = [ProductLocal::class])
abstract class AppDataBase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDataBase::class.java,
                    "generic_product_list"
                )
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}
