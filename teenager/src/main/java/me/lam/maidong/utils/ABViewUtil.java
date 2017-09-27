package me.lam.maidong.utils;

/**
 * Created by 1 on 2015/11/13.
 */

import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.SparseArray;
import android.view.View;

public class ABViewUtil
{


    public static void calcViewMeasure(View paramView)
    {
        paramView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
    }

    public static int getViewMeasuredHeight(View paramView)
    {
        calcViewMeasure(paramView);
        return paramView.getMeasuredHeight();
    }

    public static int getViewMeasuredWidth(View paramView)
    {
        calcViewMeasure(paramView);
        return paramView.getMeasuredWidth();
    }



}
