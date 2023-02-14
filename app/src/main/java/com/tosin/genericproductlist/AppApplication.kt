package com.tosin.genericproductlist

import android.app.Application
import android.content.Context
import com.tosin.genericproductlist.data.database.datasource.AppDataBase

class AppApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: AppApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    val database: AppDataBase by lazy { AppDataBase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }
}
