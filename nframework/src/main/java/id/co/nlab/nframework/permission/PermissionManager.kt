package id.co.nlab.nframework.permission

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import java.util.*

interface PermissionListener{
    fun onPermissionGranted(tag: String, permissions: Array<out String>)
    fun onPermissionDenied(tag: String, permissions: Array<out String>)
    fun onPermissionDisabled(tag: String, permissions: Array<out String>)
}

class PermissionManager(private var activity: Activity, private var listener: PermissionListener) {

    private val REQ_PERMISSION = 6521
    private var TAG = ""

    companion object {
        fun isGranted(context: Context, permission: String): Boolean{
            // Check device OS
            if (Build.VERSION.SDK_INT <= 22) {
                // When device OS is 22 or below,
                // don't worry, it's always enabled
                return true
            }

            return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun isGranted(context: Context, permission: String): Boolean{
        return PermissionManager.isGranted(context, permission)
    }

    fun check(tag: String, permissions: Array<out String>) {
        TAG = tag

        // Check device OS
        if (Build.VERSION.SDK_INT <= 22) {
            // When device OS is 22 or below,
            // don't worry, it's always enabled
            listener.onPermissionGranted(TAG, permissions)
            return
        }

        // If list not empty, request all permissions
        if(permissions.isNotEmpty())
            ActivityCompat.requestPermissions(activity, permissions, REQ_PERMISSION)
    }

    fun result(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        // Check requestCode
        if(requestCode == REQ_PERMISSION){

            // Vessel of all granted permissions
            val granted = ArrayList<String>()
            // Vessel of all denied permissions
            val denied = ArrayList<String>()
            // Vessel of all permissions which are
            // denied and rationale checkbox is checked
            val disabled = ArrayList<String>()

            // Iterate all permissions then
            // put every single permission to each vessel
            for (i in 0 until permissions.size){
                when {
                    // Permission granted
                    grantResults[i] == PackageManager.PERMISSION_GRANTED -> granted.add(permissions[i])
                    // Permission denied
                    ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i]) -> denied.add(permissions[i])
                    // Can't show rationale of permission
                    else -> disabled.add(permissions[i])
                }
            }

            if(granted.isNotEmpty()) listener.onPermissionGranted(TAG, granted.array())
            if(denied.isNotEmpty()) listener.onPermissionDenied(TAG, denied.array())
            if(disabled.isNotEmpty()) listener.onPermissionDisabled(TAG, disabled.array())
        }
    }

    fun alert(body: String, positive: String, negative: String){
        AlertDialog.Builder(activity)
                .setMessage(body)
                .setPositiveButton(positive){ dialog, _ ->
                    Intent().apply {
                        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        data = Uri.fromParts("package", activity.packageName, null)
                    }.also { activity.startActivity(it) }
                    dialog.dismiss()
                }
                .setNegativeButton(negative, null)
                .setCancelable(true)
                .show()
    }

    private fun ArrayList<String>.array(): Array<out String>{
        return this.toArray(arrayOfNulls(this.size))
    }
}