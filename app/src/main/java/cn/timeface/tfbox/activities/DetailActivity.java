package cn.timeface.tfbox.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.timeface.tfbox.R;
import cn.timeface.tfbox.adapters.DetailPhotoAdapter;
import cn.timeface.tfbox.bases.BaseActivity;
import cn.timeface.tfbox.models.CollectObj;
import cn.timeface.tfbox.models.MovieObj;
import cn.timeface.tfbox.utils.BitmapUtils;
import cn.timeface.tfbox.views.NoScrollGridView;

public class DetailActivity extends BaseActivity {
    MovieObj movieObj;
    @InjectView(R.id.ivConer)
    ImageView mIvConer;
    @InjectView(R.id.tvName)
    TextView mTvName;
    @InjectView(R.id.textClock)
    TextClock mTextClock;
    @InjectView(R.id.directorContainer)
    LinearLayout mDirectorContainer;
    @InjectView(R.id.actContainer)
    LinearLayout mActContainer;
    @InjectView(R.id.tvOtherInfo)
    TextView mTvOtherInfo;
    @InjectView(R.id.tvSummary)
    TextView mTvSummary;
    @InjectView(R.id.ivConerReflection)
    ImageView mIvConerReflection;
    @InjectView(R.id.gvPhotos)
    NoScrollGridView mGvPhotos;
    DetailPhotoAdapter mAdapter;
    @InjectView(R.id.gvMain)
    ScrollView mGvMain;
    @InjectView(R.id.btnPlay)
    Button mBtnPlay;
    @InjectView(R.id.btnCollect)
    Button mBtnCollect;

    public static void open(Context context, MovieObj movieObj) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("movie_info", movieObj);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.inject(this);

        this.movieObj = (MovieObj) getIntent().getSerializableExtra("movie_info");

        Bitmap bmpCover = BitmapFactory.decodeFile(movieObj.getMovieCover());
        mIvConerReflection.setImageBitmap(BitmapUtils.createReflectedBitmap(bmpCover, .5f));
        mIvConer.setImageBitmap(bmpCover);

        mTvName.setText(movieObj.getMovieName());

        fillUserContent(mDirectorContainer, movieObj.getDirectors());
        fillUserContent(mActContainer, movieObj.getCasts());
        

        StringBuilder tag = new StringBuilder();
        for (int i = 0; i < movieObj.getGenres().length; i++) {
            String genre = movieObj.getGenres()[i];
            tag.append(genre);
            if (i != movieObj.getGenres().length - 1) {
                tag.append(",");
            }
        }

        mTvOtherInfo.setText(String.format(getText(R.string.detail_other_info).toString(), tag.toString(), movieObj.getYear()));

        mTvSummary.setText(movieObj.getSummary());

        setCollectState();

        List<String> photos = new ArrayList<>(10);
        for (String pho : movieObj.getPhotos()) {
            photos.add(movieObj.getInfoPath() + "/" + movieObj.getMovieName() + "/" + pho);
        }

        mAdapter = new DetailPhotoAdapter(this, photos);
        mGvPhotos.setAdapter(mAdapter);
        mGvMain.smoothScrollTo(0, 0);
    }

    private void fillUserContent(LinearLayout container, String[] values) {
        for (String value : values) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(30, 0, 30, 0);
            TextView tv = new TextView(this);
            tv.setText(value);
            tv.setTextColor(getResources().getColor(R.color.text_color1));
            tv.setTextSize(18);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundResource(R.drawable.detail_tag);
            container.addView(tv);
        }
    }

    public void onPlay(View view) {
        PlayerActivity.open(this, movieObj);
    }

    public void onCollect(View view) {
        if (CollectObj.getById(this.movieObj.getMovieId()) != null) {
            CollectObj.getById(this.movieObj.getMovieId()).delete();
        } else {
            new CollectObj(this.movieObj.getMovieId()).save();
        }
        setCollectState();
    }

    private void setCollectState() {
        boolean isCollect = CollectObj.getById(this.movieObj.getMovieId()) != null;
        if (isCollect) {
        mBtnCollect.setBackgroundResource(R.drawable.selector_detail_collected);
        } else {
            mBtnCollect.setBackgroundResource(R.drawable.selector_detail_collect);
        }
    }
}
