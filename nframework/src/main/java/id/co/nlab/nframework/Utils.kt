package id.co.nlab.nframework

import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import com.google.gson.reflect.TypeToken

/** Generate generic type for gson. example : List<User>()*/
inline fun <reified  T> genericType() = object : TypeToken<T>(){}.type

/** Run time permission  */
fun hasPermission(context: Context,permissions:Array<String>):Boolean{
    for (permission in permissions){
        if(ActivityCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED){
            return false
        } }
    return true
}