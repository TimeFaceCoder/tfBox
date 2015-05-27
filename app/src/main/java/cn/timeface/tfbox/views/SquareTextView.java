package cn.timeface.tfbox.views;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by rayboot on 15/5/25.
 */
public class SquareTextView extends TextView {

    public SquareTextView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
