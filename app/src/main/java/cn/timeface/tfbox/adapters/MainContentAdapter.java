package cn.timeface.tfbox.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.timeface.tfbox.R;
import cn.timeface.tfbox.bases.BaseListAdapter;
import cn.timeface.tfbox.models.MovieObj;
import cn.timeface.tfbox.views.ScaledImageView;

/**
 * Created by rayboot on 15/5/22.
 */
public class MainContentAdapter extends BaseListAdapter<MovieObj> {
    public MainContentAdapter(Context context, List<MovieObj> listData) {
        super(context, listData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_main_content, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MovieObj item = (MovieObj) getItem(position);

        Picasso.with(mContext).load(new File(item.getMovieCover())).fit().centerInside().into(viewHolder.mIvImage);
        viewHolder.mTvName.setText(item.getMovieName());
        convertView.setTag(R.string.tag_obj, item);
        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_main_content.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @InjectView(R.id.ivImage)
        ScaledImageView mIvImage;
        @InjectView(R.id.tvName)
        TextView mTvName;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
