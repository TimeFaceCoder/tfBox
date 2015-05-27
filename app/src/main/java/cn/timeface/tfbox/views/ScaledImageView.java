package cn.timeface.tfbox.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import cn.timeface.tfbox.R;


/**
 * @author rayboot
 * @from 14-3-18 15:28
 * @TODO 高度和宽度固定比例的imageview
 */
public class ScaledImageView extends ImageView {
    private float sW = -1;
    private float sH = -1;
    private boolean isFixWidth = true;

    public ScaledImageView(Context context) {
        super(context);
    }

    public ScaledImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ScaledImageView(Context context, float sW, float sH,
                           boolean isFixWidth) {
        super(context);
        this.sW = sW;
        this.sH = sH;
        this.isFixWidth = isFixWidth;
    }

    public ScaledImageView(Context context, float sW, float sH) {
        super(context);
        this.sW = sW;
        this.sH = sH;
        isFixWidth = true;
    }

    public ScaledImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.ScaledImageView);
        sW = array.getInteger(R.styleable.ScaledImageView_sWidth, -1);
        sH = array.getInteger(R.styleable.ScaledImageView_sHeight, -1);
        isFixWidth = array.getBoolean(R.styleable.ScaledImageView_isFixWidth,
                true);
        array.recycle(); // 一定要调用，否则这次的设定会对下次的使用造成影响
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (sW == -1) {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
        } else {
            if (isFixWidth) {
                setMeasuredDimension(getMeasuredWidth(),
                        (int) (getMeasuredWidth() * sH / sW));
            } else {
                int h = getMeasuredHeight();
                setMeasuredDimension((int) (h * sW / sH), h);
            }
        }
    }
}
