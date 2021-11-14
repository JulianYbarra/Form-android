package com.junka.form.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.junka.form.R
import com.junka.form.databinding.FragmentCreateUserBinding
import java.util.*

class CreateUserFragment : Fragment() {

    private lateinit var binding: FragmentCreateUserBinding

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
    }

    fun initView() = with(binding) {
        userBornButton.apply {
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

                            userBornTextInputEditText.setText(date)
                        }
                    }.show(childFragmentManager, TAG)
            }
        }

        saveButton.apply {
            setOnClickListener {

            }
        }
    }

    companion object {
        val TAG = this::class.java.name
    }
}