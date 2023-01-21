package com.rxyzent.weatherapp.core.app

import android.app.Application
import com.rxyzent.weatherapp.core.Db.Cache

class App:Application() {
    override fun onCreate() {
        super.onCreate()

        Cache.init(this)
    }
}