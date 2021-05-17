package com.goddoro.module.utils.component

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/**
 * Created by goddoro on 2021-05-17.
 */

class SquareImageView : AppCompatImageView {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    ) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        val height = measuredHeight

        // Optimization so we don't measure twice unless we need to
//        if(DBG){
//            Log.d(TAG,"width : " + width + "   , height : " + height);
//        }
        if (width != height) {
            setMeasuredDimension(width, (width * 1.3).toInt())
        }
    }

    companion object {
        private const val TAG = "SquareImageView"
    }
}