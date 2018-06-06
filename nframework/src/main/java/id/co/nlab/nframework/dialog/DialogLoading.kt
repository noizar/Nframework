package id.co.nlab.nframework.dialog

import android.app.Activity
import android.app.ProgressDialog

/**
 * Created by noizar on 18/03/18.
 */

class DialogLoading constructor(activity:Activity){
    private val loading = ProgressDialog(activity).apply {
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }

    fun show() = loading.show()
    fun dismiss() = loading.dismiss()

    /** show and dismiss dialog depending on status true/false */
    fun showDialog(status:Boolean,message:String?){
        when(status){
            true -> loading.setMessage(message).also { show() }
            false -> dismiss()
        }
    }
}