package cn.timeface.tfbox.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.timeface.tfbox.R;
import cn.timeface.tfbox.bases.BaseListAdapter;
import cn.timeface.tfbox.views.SquareTextView;

/**
 * Created by rayboot on 15/5/25.
 */
public class SearchKeyAdapter extends BaseListAdapter<String> {
    public SearchKeyAdapter(Context context, List<String> listData) {
        super(context, listData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SquareTextView squareTextView = new SquareTextView(mContext);
        String key = (String) getItem(position);
        squareTextView.setText(key);
        squareTextView.setTextColor(Color.WHITE);
        squareTextView.setGravity(Gravity.CENTER);
        squareTextView.setTextSize(20);
        squareTextView.setBackgroundResource(R.drawable.selector_search_key_bg);
        return squareTextView;
    }
}
