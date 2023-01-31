package com.fftsun.android.lib

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

fun View.setLeftMargin(left: Int) {
    val lp = this.layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.leftMargin = left
        this.layoutParams = lp
    }
}

fun View.setTopMargin(top: Int) {
    val lp = this.layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.topMargin = top
        this.layoutParams = lp
    }
}

fun View.setRightMargin(right: Int) {
    val lp = this.layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.rightMargin = right
        this.layoutParams = lp
    }
}

fun View.setBottomMargin(bottom: Int) {
    val lp = this.layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.bottomMargin = bottom
        this.layoutParams = lp
    }
}

fun View.setStartMargin(start: Int) {
    val lp = this.layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.marginStart = start
        this.layoutParams = lp
    }
}

fun View.setEndMargin(end: Int) {
    val lp = this.layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.marginEnd = end
        this.layoutParams = lp
    }
}

fun View.setMargin(left: Int, top: Int, right: Int, bottom: Int) {
    val lp = this.layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.setMargins(left, top, right, bottom)
        this.layoutParams = lp
    }
}

fun View.setMarginsRelative(start: Int, top: Int, end: Int, bottom: Int) {
    val lp = this.layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.marginStart = start
        lp.marginEnd = end
        lp.topMargin = top
        lp.bottomMargin = bottom
        this.layoutParams = lp
    }
}

fun View.setScale(scaleX: Float, scaleY: Float) {
    this.scaleX = scaleX
    this.scaleY = scaleY
}

fun View.setPivot(pivotX: Float, pivotY: Float) {
    this.pivotX = pivotX
    this.pivotY = pivotY
}

fun View.setWidth(width: Int) {
    val lp = this.layoutParams
    if (lp is ViewGroup.LayoutParams) {
        lp.width = width
        this.layoutParams = lp
    }
}

fun View.setHeight(height: Int) {
    val lp = this.layoutParams
    if (lp is ViewGroup.LayoutParams) {
        lp.height = height
        this.layoutParams = lp
    }
}

fun View.setWidthHeight(width: Int, height: Int) {
    val params = this.layoutParams
    if (params is ViewGroup.LayoutParams) {
        params.height = height
        params.width = width
        this.layoutParams = params
    }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.setLayoutGravity(layoutGravity: Int) {
    val lp = this.layoutParams
    if (lp is LinearLayout.LayoutParams) {
        lp.gravity = layoutGravity
        this.layoutParams = lp
    } else if (lp is FrameLayout.LayoutParams) {
        lp.gravity = layoutGravity
    } else {
        Log.e("ViewExt", "setLayoutGravity not supported")
    }
}

internal object ViewClickSafe {
    var hash: Int = 0
    var lastClickTime: Long = 0
    var SPACE_TIME: Long = 1000
}

fun View.onClickSafe(clickAction: () -> Unit) {
    this.setOnClickListener {
        if (this.hashCode() != ViewClickSafe.hash) {
            ViewClickSafe.hash = this.hashCode()
            ViewClickSafe.lastClickTime = System.currentTimeMillis()
            clickAction()
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - ViewClickSafe.lastClickTime > ViewClickSafe.SPACE_TIME) {
                ViewClickSafe.lastClickTime = System.currentTimeMillis()
                clickAction()
            }
        }
    }
}

internal object ViewClickThrottle {
    var hash: Int = 0
    var lastClickTime: Long = 0
    var SPACE_TIME: Long = 1000
}

fun <T : View> T.onClickThrottle(windowMs: Long = 1000, clickAction: T.() -> Unit) {
    this.setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (currentTime - ViewClickThrottle.lastClickTime > ViewClickThrottle.SPACE_TIME || hashCode() != ViewClickThrottle.hash) {
            ViewClickThrottle.hash = this.hashCode()
            ViewClickThrottle.lastClickTime = currentTime
            ViewClickThrottle.SPACE_TIME = windowMs
            clickAction()
        }
    }
}

fun TextView.hideWhenEmpty() {
    visibility = if (text.isNullOrEmpty()) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

fun DialogFragment.showAllowingStateLoss(manager: FragmentManager, tag: String?) {
    manager.beginTransaction().add(this, tag).commitAllowingStateLoss()
}
