package org.elsys.nutritionx.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Screen {
    public static float dpToPx(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
