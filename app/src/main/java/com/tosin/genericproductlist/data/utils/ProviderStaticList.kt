package com.tosin.genericproductlist.data.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tosin.genericproductlist.data.database.entity.ProductLocal
import java.io.IOException

object ProviderStaticList {

    fun getList(context: Context, fileName: String): List<ProductLocal> {
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open(fileName)
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            println(ioException.message)
        }
        val listProductType = object : TypeToken<List<ProductLocal>>() {}.type
        val gson = Gson()
        return gson.fromJson(jsonString, listProductType)
    }
}
