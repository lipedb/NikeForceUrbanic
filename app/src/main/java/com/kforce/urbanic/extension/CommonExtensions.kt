package com.kforce.urbanic.extension

/**
 *  Check if object or instance is not null
 */
inline fun <T> T?.notNull(block: T.() -> Unit): T? {
    this?.block()
    return this@notNull
}

/**
 *  Check if object or instance is null
 */
inline fun <T> T?.isNull(block: T?.() -> Unit): T? {
    if (this == null) block()
    return this@isNull
}