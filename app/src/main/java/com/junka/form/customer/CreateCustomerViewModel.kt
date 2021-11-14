package com.junka.form.customer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.junka.form.common.Outcome
import com.junka.form.model.Customer
import com.junka.form.customer.repository.CustomerRepository

class CreateCustomerViewModel(
    private val customerRepository : CustomerRepository
) : ViewModel(){

    val outcome by lazy { MutableLiveData<Outcome<CreateCustomerAction>>() }

    fun saveCustomer(customer: Customer){
        customerRepository.save(customer)
            .addOnSuccessListener { outcome.postValue(Outcome.success(CreateCustomerAction.OnCreateCustomerSuccess(customer))) }
            .addOnFailureListener { outcome.postValue(Outcome.failure(Throwable("Algo salio mal"))) }
    }
}

sealed class CreateCustomerAction{
    class OnCreateCustomerSuccess(val customer : Customer) : CreateCustomerAction()
}