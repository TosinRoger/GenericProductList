package com.tosin.genericproductlist.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tosin.genericproductlist.AppApplication
import com.tosin.genericproductlist.R
import com.tosin.genericproductlist.data.database.datasource.DoQueriesToLoadProduct
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            withContext(IO) {
                addFirstDataInDB()
            }
        }
    }

    private suspend fun addFirstDataInDB() {
        val aux = (application as AppApplication).database.productDao().fetchProductFromJson()

        val list = aux.first()

        if (list.isEmpty()) {
            // Product from JSON
            val json = DoQueriesToLoadProduct()

            for (index in DoQueriesToLoadProduct.PAGE_ONE..DoQueriesToLoadProduct.PAGE_FOUR) {
                json.fetchProduct(index)
                    .forEach { productLocal ->
                        (application as AppApplication)
                            .database
                            .productDao()
                            .insertProduct(productLocal)
                    }
            }
        }
    }
}
