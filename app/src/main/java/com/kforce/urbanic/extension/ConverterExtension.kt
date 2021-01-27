package com.kforce.urbanic.extension

import android.graphics.Color.parseColor
import android.graphics.drawable.ColorDrawable

class ConverterExtension {
    companion object {
        private const val FLOAT_IDENTIFIER = "f"
        @JvmStatic fun convertStringToFloat(text: String): Float {
            return when {
                text.endsWith(FLOAT_IDENTIFIER) -> { text.toFloat() }
                else -> {
                    val transformedText = text + FLOAT_IDENTIFIER
                    transformedText.toFloat()
                }
            }
        }
        @JvmStatic fun convertStringToInt(text: String): Int {
            return text.toInt()
        }
        @JvmStatic fun convertIntToString(value: Int): String? {
            return value.toString()
        }

        @JvmStatic fun convertColorIntToDrawable(color: Int): ColorDrawable? {
            return if (color != 0) ColorDrawable(color) else null
        }

        @JvmStatic fun convertColorStringToDrawable(color: String): ColorDrawable? {
            val colorInt = parseColor(color)
            return if (colorInt != 0) ColorDrawable(colorInt) else null
        }
    }
}