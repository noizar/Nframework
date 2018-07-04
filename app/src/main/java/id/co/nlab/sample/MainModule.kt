package id.co.nlab.sample

import android.content.Context
import id.co.nlab.nframework.base.Module
import id.co.nlab.nframework.base.ViewState

interface test{
    fun onLoading()
    fun onSuccess()
    fun onFailure()
    fun onUpdate()
}

class MainModule constructor(context: Context,view:ViewState) :Module(context,view),test {
    val tagLoading = "loading"
    val tagSuccess = "success"
    val tagFailure = "failure"
    val tagUpdate  = "update"

    override fun onSuccess() {
        network.networkConfiguration(tagSuccess)

        network.success("","Success")
    }

    override fun onFailure() {
        network.networkConfiguration(tagFailure)
        network.failure("","failure")
    }

    override fun onLoading() {
        network.networkConfiguration(tagLoading)
        network.loading(true,"Sync Data")
    }

    override fun onUpdate() {
        network.networkConfiguration(tagUpdate)
        network.update("","Update")
    }


}