package id.co.nlab.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import id.co.nlab.nframework.validation.Validation
import id.co.nlab.nframework.validation.ValidationDelegate
import kotlinx.android.synthetic.main.activity_validation.*

class SampleValidation :AppCompatActivity(),ValidationDelegate {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validation)

        var validator = Validation(this)

        validator.apply {
            registerField().requiredRule(username,"Invalid username","username")
            registerField().emailRule(email,"invalid email","email")
            registerField().confirmationRules(confrim_email,email,"Email not macth","confrim")
            registerField().regexRule(password,paswword_input,"^(?=.*[0-9])",
                    "Invalid Password Combination","password")
            validator.registerField().lenghtRule(password,paswword_input,4,5,"Invalid Lenght","password")
        }

        register.setOnClickListener{
            validator.validation()
        }
    }

    override fun validationSuccess(data: HashMap<String, String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}