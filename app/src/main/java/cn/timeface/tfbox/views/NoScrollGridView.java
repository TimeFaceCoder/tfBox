package cn.timeface.tfbox.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author LiuCongshan
 * @from 2014-5-5上午11:20:03
 * @TODO 无上下滚动的GridView，可内嵌到ScrollView内
 * 注意在使用中，height 设置为 wrap_content
 */
public class NoScrollGridView extends GridView {
    public NoScrollGridView(Context context) {
        super(context);

    }

    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec;

        if (getLayoutParams().height == LayoutParams.WRAP_CONTENT) {
            heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                    MeasureSpec.AT_MOST);
        } else {
            // Any other height should be respected as is.
            heightSpec = heightMeasureSpec;
        }
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
