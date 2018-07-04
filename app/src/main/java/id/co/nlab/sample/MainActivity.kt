package id.co.nlab.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.co.nlab.nframework.base.ViewState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),ViewState {
    private val module by lazy { MainModule(applicationContext,this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupView()
    }

    override fun onLoading(status: Boolean, tag: String?, message: String?) {
        runOnUiThread { Toast.makeText(this,message,Toast.LENGTH_SHORT).show()}
    }

    override fun onSuccess(data: Any?, tag: String?, message: String?) {
        runOnUiThread { Toast.makeText(this,message,Toast.LENGTH_SHORT).show()}

    }

    override fun onFailure(data: Any?, tag: String?, message: String?) {
        runOnUiThread { Toast.makeText(this,message,Toast.LENGTH_SHORT).show()}

    }

    override fun onUpdate(data: Any?, tag: String?, message: String?) {
        runOnUiThread { Toast.makeText(this,message,Toast.LENGTH_SHORT).show()}

    }

    override fun setupView() {

    }
}
