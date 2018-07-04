package id.co.nlab.nframework.base
import android.content.Context
import kotlin.properties.Delegates


/** interface for implement on view (fragment or Activity)*/
interface ViewState{
     fun onLoading(status:Boolean,tag:String?,message:String?)
     fun onSuccess(data:Any?,tag: String?,message: String?)
     fun onFailure(data:Any?,tag: String?,message: String?)
     fun onUpdate(data:Any?,tag: String?,message: String?)
     fun setupView()
}

interface DialogViewState{
    fun onSendData(data:Any?,tag:String)
}

class Network(view:ViewState){
   private sealed class NetworkState{
        class CreateState:NetworkState()
        class Loading(val isLoading:Boolean,val tag:String, val message:String):NetworkState()
        class Success<out T>(val data:T?,val tag:String,val message:String):NetworkState()
        class Failure<out T>(val data:T?,val tag:String,val message:String):NetworkState()
        class Update<out T>(val data:T?,val tag:String,val message:String):NetworkState()
    }

    private lateinit var tag:String

    private var newtwork:NetworkState by Delegates.observable<NetworkState>(NetworkState.CreateState()){
        _,_,state -> when(state){
        is NetworkState.Loading    -> view.onLoading(state.isLoading,state.tag,state.message)
        is NetworkState.Success<*> -> view.onSuccess(state.data,state.tag,state.message)
        is NetworkState.Failure<*> -> view.onFailure(state.data,state.tag,state.message)
        is NetworkState.Update<*>  -> view.onUpdate(state.data,state.tag,state.message) }
    }

    fun networkConfiguration(tag:String){ this.tag = tag }

    fun loading(isLoading: Boolean,message:String){ newtwork = NetworkState.Loading(isLoading,tag,message) }
    fun success(data:Any?,message: String){ newtwork = NetworkState.Success(data,tag,message) }
    fun failure(data:Any?,message:String){ newtwork = NetworkState.Failure(data,tag,message)}
    fun update(data:Any?,message:String){newtwork = NetworkState.Update(data,tag,message)}

}

/** interface for implement in Module Class */
open class Module (val contextView: Context, viewState: ViewState){
     val network = Network(viewState)
}


