package com.suiding.util;

import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout.LayoutParams;

public class MeasureUtil {

    @SuppressWarnings("deprecation")
    public static Point measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new LayoutParams(
                    LayoutParams.FILL_PARENT, 
                    LayoutParams.WRAP_CONTENT);
        }
        int childHeightSpec;
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        if (p.height > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(p.height, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(childWidthSpec, childHeightSpec);
        return new Point(view.getMeasuredWidth(), view.getMeasuredHeight());
    }

}
