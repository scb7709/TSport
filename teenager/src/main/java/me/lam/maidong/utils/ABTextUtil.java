package me.lam.maidong.utils;

/**
 * Created by 1 on 2015/11/13.
 */

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Collection;

public class ABTextUtil {
    public static int dip2px(Context paramContext, float paramFloat) {
        return (int) (0.5F + paramFloat * paramContext.getResources().getDisplayMetrics().density);
    }

    public static float getScaledDensity(Context paramContext) {
        return paramContext.getResources().getDisplayMetrics().scaledDensity;
    }

    /*public static boolean isBlank(CharSequence paramCharSequence) {
        if ((paramCharSequence == null) || (paramCharSequence.toString().trim().length() <= 0)) ;
        for (int i = 1; ; i = 0)
            return i;
    }

    public static boolean isEmpty(CharSequence paramCharSequence) {
        if ((paramCharSequence == null) || (paramCharSequence.length() <= 0)) ;
        for (int i = 1; ; i = 0)
            return i;
    }*/

    public static boolean isEmpty(Collection paramCollection) {
        if (paramCollection == null) ;
        for (boolean bool = true; ; bool = paramCollection.isEmpty())
            return bool;
    }

    public static int px2dip(Context paramContext, float paramFloat) {
        return (int) (0.5F + paramFloat / paramContext.getResources().getDisplayMetrics().density);
    }

    public static int px2sp(Context paramContext, float paramFloat) {
        return (int) (0.5F + paramFloat / paramContext.getResources().getDisplayMetrics().scaledDensity);
    }

    public static int sp2px(Context paramContext, float paramFloat) {
        return (int) (0.5F + paramFloat * paramContext.getResources().getDisplayMetrics().scaledDensity);
    }
}

