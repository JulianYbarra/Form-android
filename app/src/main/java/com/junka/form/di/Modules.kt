package com.junka.form.di


import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.junka.form.customer.di.customerModule
import org.koin.dsl.module

val appModule = module{
    single { Firebase.database.reference  }
}

val appModules = listOf(appModule,customerModule)