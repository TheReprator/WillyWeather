package reprator.willyWeather.base.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
public fun Any?.isNull(): Boolean {
    contract {
        returns(false) implies (this@isNull != null)
    }

    return this == null
}

@OptIn(ExperimentalContracts::class)
public fun Any?.isNotNull(): Boolean {
    contract {
        returns(true) implies (this@isNotNull != null)
    }
    return this != null
}

fun Any?.whatIfNotNull(func: (Any?) -> Unit) {
   if(this.isNotNull())
       func(this)
}


fun<T> Any?.returnNullIfEqualElseValue(other: Any): T? {
    return if (this == other)
          null
    else
        this as T
}
