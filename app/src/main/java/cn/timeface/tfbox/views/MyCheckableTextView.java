package cn.timeface.tfbox.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.TextView;

import cn.timeface.tfbox.R;

/**
 * Created by rayboot on 15/5/26.
 */
public class MyCheckableTextView extends FrameLayout implements Checkable{

    private TextView mText;
    public MyCheckableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public MyCheckableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyCheckableTextView (Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context){
        // 填充布局
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_main_type, this);
        mText = (TextView) v.findViewById(R.id.tvName);
    }

    @Override
    public void setChecked( boolean checked) {
        mText.setSelected(checked);
    }

    @Override
    public boolean isChecked() {
        return mText.isSelected();
    }

    @Override
    public void toggle() {
        if (isChecked()) {
            setChecked(false);
        } else {
            setChecked(true);
        }
    }

    public void setTitle(String text){
        mText.setText(text);
    }
}
