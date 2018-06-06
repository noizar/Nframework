package id.co.nlab.nframework.validation

import android.util.Patterns
import android.widget.EditText
import java.util.regex.Pattern

interface ValidationRules{
    fun validation():Boolean
    fun getData():String
}



class ConfrimationRule constructor(val editText:EditText, val confrimEditText:EditText):ValidationRules{
    override fun validation(): Boolean = confrimEditText.text.toString() == editText.text.toString()
    override fun getData(): String  = editText.text.toString()
}

class EmailRule constructor(var editText: EditText):ValidationRules{
    override fun validation(): Boolean = Patterns.EMAIL_ADDRESS.matcher(editText.text.toString()).matches()
    override fun getData(): String = editText.text.toString()
}

class LengthRule constructor(val editText: EditText,val minLenght:Int,val maxLength:Int):ValidationRules{
    override fun validation(): Boolean = editText.text.length in minLenght..maxLength
    override fun getData(): String = editText.text.toString()
}

class RegexRule constructor(val editText: EditText,var regex:String):ValidationRules{
    override fun validation(): Boolean = Pattern.compile(regex).matcher(editText.text.toString()).find()
    override fun getData(): String = editText.text.toString()
}

class RequireRule constructor(val editText: EditText):ValidationRules{
    override fun validation(): Boolean = editText.text.isNotEmpty()

    override fun getData(): String = editText.text.toString()
}
