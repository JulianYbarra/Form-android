package com.junka.form.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.junka.form.R
import com.junka.form.common.Outcome
import com.junka.form.common.extension.validate
import com.junka.form.common.extension.validateNotEmpty
import com.junka.form.databinding.FragmentCreateUserBinding
import com.junka.form.login.LoginActivity
import com.junka.form.model.Customer
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class CreateCustomerFragment : Fragment() {

    private lateinit var binding: FragmentCreateUserBinding
    private val viewModel by viewModel<CreateCustomerViewModel>()

    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    var date = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateUserBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        listenToObserver()
    }

    private fun listenToObserver() {
        viewModel.outcome.observe(viewLifecycleOwner) {
            when (it) {
                is Outcome.Failure -> processError(it.e)
                is Outcome.Success -> processOnSuccess(it.data)
            }
        }
    }

    private fun initView() = with(binding) {

        nameTextInputLayout.apply {
            validate(getString(R.string.field_cant_be_empty))
        }
        lastNameTextInputLayout.apply {
            validate(getString(R.string.field_cant_be_empty))
        }
        ageTextInputLayout.apply {
            validate(getString(R.string.field_cant_be_empty))
        }
        birthTextInputLayout.apply {
            validate(getString(R.string.field_cant_be_empty))
        }

        birthButton.apply {
            setOnClickListener {
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText(R.string.user_born)
                    .setSelection(calendar.timeInMillis)
                    .build()
                    .apply {
                        addOnPositiveButtonClickListener {
                            calendar.timeInMillis = it

                            val year = calendar.get(Calendar.YEAR)
                            val month = calendar.get(Calendar.MONTH)
                            val day = calendar.get(Calendar.DAY_OF_MONTH)
                            date = "$day/$month/$year"
                            if (it > System.currentTimeMillis()){
                                birthTextInputEditText.setText("")
                                Toast.makeText(requireContext(),"La fecha no puede ser mayor a hoy",Toast.LENGTH_SHORT).show()
                                return@addOnPositiveButtonClickListener
                            }
                            birthTextInputEditText.setText(date)
                        }
                    }.show(childFragmentManager, TAG)
            }
        }

        saveButton.apply {
            setOnClickListener {
                saveButton.isEnabled = false

                validForm()?.let {
                    viewModel.saveCustomer(it)

                    //clean text
                    with(binding) {
                        nameTextInputLayout.editText?.setText("")
                        lastNameTextInputLayout.editText?.setText("")
                        ageTextInputLayout.editText?.setText("")
                        birthTextInputLayout.editText?.setText("")
                    }
                } ?: run{
                    saveButton.isEnabled = true
                }
            }
        }
    }

    private fun processOnSuccess(action: CreateCustomerAction) {
        when (action) {
            is CreateCustomerAction.OnCreateCustomerSuccess -> onCreateUser(action.customer)
        }
    }

    private fun processError(error: Throwable) {
        error.printStackTrace()
        Toast.makeText(requireContext(), error.message.toString(), Toast.LENGTH_LONG).show()
    }

    private fun onCreateUser(customer: Customer) {
        binding.saveButton.isEnabled = true
        Toast.makeText(requireContext(), getString(R.string.save_customer_succes,customer.name), Toast.LENGTH_SHORT).show()
    }

    private fun validForm(): Customer? {
        var isValid = true

        with(binding){
            val name = nameTextInputLayout.validateNotEmpty(getString(R.string.invalid_value))
            val lastName = lastNameTextInputLayout.validateNotEmpty(getString(R.string.invalid_value))
            val age = ageTextInputLayout.validateNotEmpty(getString(R.string.invalid_value))
            val birth = birthTextInputLayout.validateNotEmpty(getString(R.string.invalid_value))

            if(name.isEmpty()){
                isValid = false
            }
            if(lastName.isEmpty()){
                isValid = false
            }
            if(age.isEmpty()){
                isValid = false
            }
            if(birth.isEmpty()){
                isValid = false
            }


            return if (isValid) Customer(name, lastName, age.toInt(), birth) else null
        }
    }

    companion object {
        val TAG = this::class.java.name
    }
}