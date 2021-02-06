package reprator.willyWeather.base_android.extensions

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import reprator.willyWeather.base_android.util.isAndroidJOrLater

fun View.drawableFromViewContext(@DrawableRes resourceId: Int) = ContextCompat.getDrawable(context, resourceId)

fun View.setbackGround(@DrawableRes drawableRes: Int) {
    if (isAndroidJOrLater)
        background = drawableFromViewContext(drawableRes)
    else
        setBackgroundDrawable(drawableFromViewContext(drawableRes))
}

fun View.shortSnackBar(msg: String) = snackBar(msg)

fun View.shortSnackBar(@StringRes msg: Int) = snackBar(context.getString(msg))

fun View.snackBar(msg: String, block: (Snackbar.() -> Unit) = {}) {
    val snackBar = Snackbar.make(
        this, msg,
        Snackbar.LENGTH_SHORT
    )
    block(snackBar)
    snackBar.show()
}