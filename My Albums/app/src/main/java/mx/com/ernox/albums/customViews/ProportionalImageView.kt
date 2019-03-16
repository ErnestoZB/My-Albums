package mx.com.ernox.albums.customViews

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

class ProportionalImageView : ImageView {

    constructor( context : Context) : super(context)

    constructor(context : Context, attrs : AttributeSet) : super(context, attrs)

    constructor(context : Context, attrs : AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    @Override
    override fun onMeasure( widthMeasureSpec : Int, heightMeasureSpec: Int)
    {
        if (drawable != null)
        {
            setMeasuredDimension(widthMeasureSpec, widthMeasureSpec)
        }
        else
        {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}