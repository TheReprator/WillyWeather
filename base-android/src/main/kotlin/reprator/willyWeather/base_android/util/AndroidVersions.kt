package reprator.willyWeather.base_android.util

import android.os.Build

val isAndroidPOrLater
    get() = Build.VERSION_CODES.P <= Build.VERSION.SDK_INT
val isAndroidOOrLater
    get() = Build.VERSION_CODES.O <= Build.VERSION.SDK_INT
val isAndroidLOrLater
    get() = Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT
val isAndroidMOrLater
    get() = Build.VERSION_CODES.M <= Build.VERSION.SDK_INT
val isAndroidNOrLater
    get() = Build.VERSION_CODES.N <= Build.VERSION.SDK_INT
val isAndroidKOrLater
    get() = Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT
val isAndroidJOrLater
    get() = Build.VERSION_CODES.JELLY_BEAN <= Build.VERSION.SDK_INT
