package com.junka.form.customer.repository

import com.google.firebase.database.DatabaseReference
import com.junka.form.model.Customer

class CustomerRepository(
    private val dbReference: DatabaseReference) {

    fun save(customer : Customer) = dbReference.child(CUSTOMERS).push().setValue(customer)

    companion object{
        const val CUSTOMERS = "customers"
    }
}