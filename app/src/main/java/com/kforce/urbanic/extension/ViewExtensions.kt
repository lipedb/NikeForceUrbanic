package com.kforce.urbanic.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import com.kforce.urbanic.util.DEFAULT_TRANSACTION_DURATION

/** Set the View visibility to VISIBLE and eventually animate the View alpha till 100% */
fun View.toVisible(animate: Boolean = true, animationDuration: Long = DEFAULT_TRANSACTION_DURATION) {
    if (animate) {
        animate().alpha(1f).setDuration(animationDuration).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                visibility = View.VISIBLE
            }
        })
    } else {
        visibility = View.VISIBLE
    }
}

/** Set the View visibility to INVISIBLE and eventually animate view alpha till 0% */
fun View.toInvisible(animate: Boolean = true) {
    hide(View.INVISIBLE, animate)
}

/** Set the View visibility to GONE and eventually animate view alpha till 0% */
fun View.toGone(animate: Boolean = true) {
    hide(View.GONE, animate)
}

/** Set the animate till 0% */
private fun View.hide(hidingStrategy: Int, animate: Boolean = true) {
    if (animate) {
        animate().alpha(0f).setDuration(DEFAULT_TRANSACTION_DURATION).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                visibility = hidingStrategy
            }
        })
    } else {
        visibility = hidingStrategy
    }
}