package com.tosin.genericproductlist.remote.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tosin.genericproductlist.remote.entity.ProductRemote
import java.io.IOException

object ProviderStaticList {

    fun getList(context: Context): List<ProductRemote> {
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("product_list.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            throw ioException
        }
        val listProductType = object : TypeToken<List<ProductRemote>>() {}.type
        val gson = Gson()
        return gson.fromJson(jsonString, listProductType)
    }
}
