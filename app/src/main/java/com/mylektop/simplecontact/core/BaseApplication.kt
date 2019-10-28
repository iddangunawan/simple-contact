package com.mylektop.simplecontact.core

import android.app.Application
import android.content.Context
import com.mylektop.simplecontact.di.AppModuleComponents
import org.koin.android.ext.android.startKoin

/**
 * Created by MyLektop on 23/10/2019.
 */
class BaseApplication : Application() {

    companion object {
        @JvmStatic
        var appContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext

        startKoin(this, AppModuleComponents)
    }
}