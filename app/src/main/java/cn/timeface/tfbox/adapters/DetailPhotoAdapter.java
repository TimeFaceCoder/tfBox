package cn.timeface.tfbox.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

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
 * Created by rayboot on 15/5/27.
 */
public class DetailPhotoAdapter extends BaseListAdapter<String> {

    public DetailPhotoAdapter(Context context, List<String> listData) {
        super(context, listData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_detail_photo, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String item = (String) getItem(position);
        Picasso.with(mContext).load(new File(item)).fit().centerInside().into(viewHolder.mIvPhoto);
        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_detail_photo.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @InjectView(R.id.ivPhoto)
        ScaledImageView mIvPhoto;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
