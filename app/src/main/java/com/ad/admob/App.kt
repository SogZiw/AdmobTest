package com.ad.admob

import android.app.Application
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

lateinit var app: App

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
        initAdmob()
    }

    private fun initAdmob() {
        CoroutineScope(Dispatchers.IO).launch { MobileAds.initialize(app) }
    }

}