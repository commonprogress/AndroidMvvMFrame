package com.base.commonality.adapter

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import android.view.MotionEvent

class ListenableViewPager : ViewPager {
    private var mListener: TouchEventListener? = null

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (mListener != null) {
            mListener!!.dispatchTouchEvent(ev)
        }
        return super.dispatchTouchEvent(ev)
    }

    fun setTouchListener(mListener: TouchEventListener?) {
        this.mListener = mListener
    }

    interface TouchEventListener {
        fun dispatchTouchEvent(ev: MotionEvent?)
    }
}