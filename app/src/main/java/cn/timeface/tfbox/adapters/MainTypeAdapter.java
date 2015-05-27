package cn.timeface.tfbox.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.timeface.tfbox.bases.BaseListAdapter;
import cn.timeface.tfbox.views.MyCheckableTextView;

/**
 * Created by rayboot on 15/5/22.
 */
public class MainTypeAdapter extends BaseListAdapter<String> {
    public MainTypeAdapter(Context context, List<String> listData) {
        super(context, listData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String item = (String) getItem(position);

        final MyCheckableTextView singleView = new MyCheckableTextView(mContext);
        singleView.setTitle(item);
        return singleView;
    }

}
