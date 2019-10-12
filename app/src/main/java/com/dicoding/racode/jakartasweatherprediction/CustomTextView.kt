package com.dicoding.racode.jakartasweatherprediction

import android.content.Context
import android.widget.TextView
import android.graphics.Typeface
import android.util.AttributeSet


class CustomTextView : TextView {

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    private fun init(context: Context) {
        val typeface = Typeface.createFromAsset(context.assets, "fonts/Inter-Medium.otf")
        setTypeface(typeface)

    }
}