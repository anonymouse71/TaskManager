package com.example.emil.taskmanager.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 5/15/2016.
 */
public class AnimationUtil {


    public static void swipeOpen(View view,Context context, int Dp) {
        view.animate()
                .translationX(convertDpToPixel(Dp,context))
                .setDuration(300)
                .start();

    }

    private static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
}
