package reprator.willyWeather.base_android.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.annotation.RestrictTo
import androidx.fragment.app.Fragment

@get:RestrictTo(RestrictTo.Scope.LIBRARY)
internal inline val isMainThread: Boolean
    get() = Looper.myLooper() == Looper.getMainLooper()

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal fun checkIsMainThread() = check(isMainThread)


fun <T, U> getCallingObjectForActivityFragment(
    referenceType: U,
    callActivity: Activity.() -> T,
    callFragment: Fragment.() -> T
): T where U : Context {
    return when (referenceType) {
        is Activity -> callActivity(referenceType)
        is Fragment -> callFragment(referenceType)
        else -> {
            throw IllegalArgumentException("Caller must be an Activity or a Fragment.")
        }
    }
}

fun  getContext( param: Any
): Context {
    return when (param) {
        is Activity -> param
        is Fragment -> param.requireContext()
        else -> {
            throw IllegalArgumentException("Caller must be an Activity or a Fragment.")
        }
    }
}

fun isAllPermissionsGranted(grantResults: IntArray): Boolean {
    var isGranted = true

    for (grantResult in grantResults) {
        isGranted = grantResult == PackageManager.PERMISSION_GRANTED

        if (!isGranted) {
            break
        }
    }

    return isGranted
}