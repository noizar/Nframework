package id.co.nlab.nframework.validation

import android.support.design.widget.TextInputLayout
import android.widget.EditText
import id.co.nlab.nframework.R


/**
 * Created by noizar on 11/1/17.
 */

interface ValidationDelegate{
    fun validationSuccess(data:HashMap<String,String>)
}

class Validation constructor(var delegate: ValidationDelegate) {
    private var registerFieldData = ArrayList<Any>()
    private var data    = HashMap<String,String>()
    private var error   = ArrayList<TextInputLayout>()
    private var errorCount = 0


    inner class registerField(){
        /** Email Rules register */
        fun emailRule(editText: EditText,message:String,key:String) = registerFieldData.
                add(ValidationType1(editText,EmailRule(editText),message,key))

        fun emailRule(editText: EditText,textInput:TextInputLayout,message: String,key: String) =
                registerFieldData.add(ValidationType2(editText,textInput,EmailRule(editText),message,key))

        /** Required Rules register */
        fun requiredRule(editText: EditText,message: String,key: String) = registerFieldData
                .add(ValidationType1(editText,RequireRule(editText),message,key))

        fun requiredRule(editText: EditText,textInput: TextInputLayout,message: String,key: String) =
                registerFieldData.add(ValidationType2(editText,textInput,RequireRule(editText),
                        message,key))

        /** Length Rule register  */
        fun lengthRule(editText: EditText,minLength:Int,maxLength:Int,message: String,key: String) =
                registerFieldData.add(ValidationType1(editText,LengthRule(editText,minLength,maxLength),
                        message,key))

        fun lenghtRule(editText: EditText,inputText:TextInputLayout,minLength: Int,maxLength: Int,message: String,key: String) =
                registerFieldData.add(ValidationType2(editText,inputText,LengthRule(editText,minLength,maxLength),
                        message,key))

        /** Confirmation Rule register */
        fun confirmationRules(editText: EditText,confriField:EditText,message: String,key: String) =
                registerFieldData.add(ValidationType1(editText,ConfrimationRule(editText,confriField),
                        message,key))

        fun confirmationRule(editText: EditText,confriField: EditText,textInput: TextInputLayout,message: String,key: String) =
                registerFieldData.add(ValidationType2(editText,textInput,ConfrimationRule(editText,confriField),
                        message,key))

        /** Regex Rule register
         *
        ^                # start-of-string
        (?=.*[0-9])       # a digit must occur at least once
        (?=.*[a-z])       # a lower case letter must occur at least once
        (?=.*[A-Z])       # an upper case letter must occur at least once
        (?=.*[@#$%^&+=])  # a special character must occur at least once you can replace with your special characters
        (?=\\S+$)         # no whitespace allowed in the entire string
        .{4,}             # anything, at least six places though
        $                 # end-of-string
         */

        fun regexRule(editText: EditText,regex:String,message: String,key: String) = registerFieldData.add(
                ValidationType1(editText,RegexRule(editText,regex),message,key))

        fun regexRule(editText: EditText,textInput:TextInputLayout,regex:String,message: String,key: String)  =
                registerFieldData.add(ValidationType2(editText,textInput,RegexRule(editText,regex),
                        message,key))

    }

    fun validation(){
        startValidate()
        for ( validation in registerFieldData){
            when(validation){
                is ValidationType1 -> type1Proses(validation)
                is ValidationType2 -> type2Proses(validation)
            }

        }

        finisValidate()
    }

    private fun startValidate(){
        errorCount = 0
        data.clear()
        error.clear()
    }

    private fun finisValidate(){
        when (errorCount){
            0 -> delegate.validationSuccess(data)
        }
    }


    private fun type1Proses(item:ValidationType1){
        when(item.method.validation()){
            true    -> data[item.key] = item.method.getData()
            false   ->errorCount++.also { item.editText.error = item.message }
        }
    }

    private fun type2Proses(item: ValidationType2){
        when(item.method.validation()){
            true ->{
                if ((!findError(item.textInput))) { item.textInput.isErrorEnabled = false }
                data[item.key] = item.method.getData()
            }

            false ->{
                item.textInput.setErrorTextAppearance(R.style.text_error)

                if(!findError(item.textInput)){
                    item.textInput.apply {
                        isErrorEnabled = true
                        error = item.message }
                }

                error.add(item.textInput)
                errorCount++
            }

        }
    }

    private fun findError(textInput:TextInputLayout):Boolean = error.contains(textInput)




}