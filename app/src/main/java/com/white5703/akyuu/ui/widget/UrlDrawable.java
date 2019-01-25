package com.white5703.akyuu.ui.widget;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by cb on 2016/12/9.
 */
public class UrlDrawable extends BitmapDrawable {
    // the drawable that you need to set, you could set the initial drawing
    // with the loading image if you need to
    protected Drawable drawable;

    @Override
    public void draw(Canvas canvas) {
        // override the draw to facilitate refresh function later
        if (drawable != null) {
            drawable.draw(canvas);
        }
    }
}