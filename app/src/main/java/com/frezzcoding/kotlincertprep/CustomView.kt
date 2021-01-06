package com.frezzcoding.kotlincertprep

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class CustomView(context : Context, attr : AttributeSet? = null) :
    ViewGroup(context, attr) {

    private lateinit var titleTextView : TextView
    private lateinit var subtitleTextView: TextView

    override fun onFinishInflate() {
        super.onFinishInflate()

        titleTextView = findViewById(R.id.title)
        subtitleTextView = findViewById(R.id.subtitle)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        TODO("Not yet implemented")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

    }



}