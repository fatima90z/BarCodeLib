package com.zetabox.zcodebareader.helpers

import android.Manifest.permission.*
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionHelper {

    companion object {


        fun checkCameraPermissions(context: Activity): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            ) {
                var permissions = listOf(CAMERA)
                val listPermissionsNeeded: MutableList<String> = ArrayList()
                permissions.forEach {
                    if (ContextCompat.checkSelfPermission(
                            context,
                            it
                        ) != PackageManager.PERMISSION_GRANTED
                    )
                        listPermissionsNeeded.add(it)
                }
                if (listPermissionsNeeded.isNotEmpty()) {
                    ActivityCompat.requestPermissions(
                        context,
                        listPermissionsNeeded.toTypedArray(), 1
                    )
                    return false
                }
                return true
            }
            return true
        }

    }
}
