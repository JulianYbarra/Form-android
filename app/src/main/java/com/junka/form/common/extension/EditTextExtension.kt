package com.junka.form.common.extension

import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout


fun TextInputLayout.validate(strError : String) {
    editText?.doAfterTextChanged {
        error = if(it.isNullOrEmpty()){
            strError
        }else{
            null
        }
    }
}


fun TextInputLayout.validateNotEmpty(strError : String) : String{
    val edText = editText?.text
    edText?.let {
        error = if (it.isEmpty()) {
            strError
        } else {
            null
        }
    }
    return edText.toString()
}
