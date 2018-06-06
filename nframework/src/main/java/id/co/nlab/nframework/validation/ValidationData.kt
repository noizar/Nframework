package id.co.nlab.nframework.validation

import android.support.design.widget.TextInputLayout
import android.widget.EditText


data class ValidationType1(var editText: EditText,val method:ValidationRules,val message:String,
                           val key:String)
data class ValidationType2(var editText: EditText,var textInput:TextInputLayout,val method: ValidationRules,
                           val message: String,val key: String)
