package com.tosin.genericproductlist

import android.app.Application
import android.content.Context

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

    override fun onCreate() {
        super.onCreate()

        instance = this
    }
}
