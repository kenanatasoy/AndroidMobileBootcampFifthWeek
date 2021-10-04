package com.example.androidmobilebootcampfifthweek

import android.app.Application
import com.example.androidmobilebootcampfifthweek.network.ServiceConnector

class MVVMApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceConnector.init()
    }
}
