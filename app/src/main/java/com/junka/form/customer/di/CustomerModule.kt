package com.junka.form.customer.di

import com.junka.form.customer.CreateCustomerViewModel
import com.junka.form.customer.repository.CustomerRepository
import org.koin.dsl.module

val customerModule = module{
    single { CreateCustomerViewModel(get()) }
    single { CustomerRepository(get()) }
}