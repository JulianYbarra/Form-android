package com.junka.form

import android.app.Application
import com.junka.form.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FormApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            // declare used Android context
            androidContext(this@FormApplication)
            // declare modules
            modules(appModules)
        }
    }
}