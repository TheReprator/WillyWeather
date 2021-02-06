package reprator.willyWeather.base_android.extensions

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import java.util.*

inline fun <reified T : Context> Context.findBaseContext(): T? {
    var ctx: Context? = this
    do {
        if (ctx is T) {
            return ctx
        }
        if (ctx is ContextWrapper) {
            ctx = ctx.baseContext
        }
    } while (ctx != null)

    // If we reach here, there's not an Context of type T in our Context hierarchy
    return null
}

fun Activity.hideSoftInput() {
    val imm: InputMethodManager? = getSystemService()
    val currentFocus = currentFocus
    if (currentFocus != null && imm != null) {
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}

fun Fragment.hideSoftInput() = requireActivity().hideSoftInput()

fun Context.getStatusBarHeight(): Int {
    val resources = this.resources
    var statusBarHeight = 0

    val resourceId = resources.getIdentifier(
        "status_bar_height", "dimen", "android"
    )

    if (resourceId > 0) {
        statusBarHeight = resources.getDimensionPixelSize(resourceId)
    }

    return statusBarHeight
}

fun Context.stringsArray(@ArrayRes msg: Int) = resources.getStringArray(msg)
fun Context.drawableArray(@ArrayRes msg: Int) = resources.obtainTypedArray(msg)

fun Context.getMergeStringResource(
    @StringRes stringResourceId: Int,
    @StringRes vararg stringArray: Int
): String {
    val totalStringArray = arrayOfNulls<String>(stringArray.size)
    for (i in stringArray.indices)
        totalStringArray[i] = getString(stringArray[i])

    return String.format(Locale.getDefault(), getString(stringResourceId), *totalStringArray)
}


fun Context.goToSettings() {
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName")).apply {
        addCategory(Intent.CATEGORY_DEFAULT)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }.also { intent ->
        startActivity(intent)
    }
}

fun Context.color(@ColorRes color: Int) = ContextCompat.getColor(this, color)
fun Context.integer(@IntegerRes integer: Int) = resources.getInteger(integer)


fun Context.shortToast(msg: String) = Toast.makeText(
    this,
    msg,
    Toast.LENGTH_SHORT
).show()

fun Context.singleButtonDialog(
    message: String, title: String = "", buttonName: String = "OK",
    buttonCallBack: () -> Unit = {}
) {
    val dialogBuilder = AlertDialog.Builder(this)
    dialogBuilder.setMessage(message)
        .setCancelable(false)
        .setPositiveButton(buttonName) { dialog, _ ->
            buttonCallBack()
            dialog.dismiss()
        }

    val alert = dialogBuilder.create()
    if (!title.isNullOrEmpty())
        alert.setTitle(title)
    alert.show()
}

fun Context.twoButtonDialog(
    message: String, title: String = "", positiveButtonName: String = "OK",
    negativeButtonName: String = "Cancel",
    positiveButtonCallBack: () -> Unit = {},
    negativeButtonCallBack: () -> Unit = {}
) {
    val dialogBuilder = AlertDialog.Builder(this)
    dialogBuilder.setMessage(message)
        .setCancelable(false)
        .setPositiveButton(positiveButtonName) { dialog, _ ->
            positiveButtonCallBack()
            dialog.dismiss()
        }.setNegativeButton(negativeButtonName) { dialog, _ ->
            negativeButtonCallBack()
            dialog.cancel()
        }

    val alert = dialogBuilder.create()
    if (!title.isNullOrEmpty())
        alert.setTitle(title)
    alert.show()
}
/*

private void buildAlertMessageNoGps() {
    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
        .setCancelable(false)
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int id) {
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int id) {
                dialog.cancel();
            }
        });
    final AlertDialog alert = builder.create();
    alert.show();
}*/
