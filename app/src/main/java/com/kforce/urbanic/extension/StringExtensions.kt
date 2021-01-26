package com.kforce.urbanic.extension

import com.kforce.urbanic.util.URL_SEPARATOR
import com.kforce.urbanic.util.WHITE_SPACE

/**
 * Build REQUEST URL
 *
 * @return the REQUEST URL
 */
fun String.buildURL(): String {
    // split the string by spaces in list
    return this.replace(WHITE_SPACE, URL_SEPARATOR)
}