package cn.timeface.tfbox.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.timeface.tfbox.R;
import cn.timeface.tfbox.activities.DetailActivity;
import cn.timeface.tfbox.adapters.MainContentAdapter;
import cn.timeface.tfbox.bases.BaseFragment;
import cn.timeface.tfbox.events.ChangeTypeEvent;
import cn.timeface.tfbox.events.SearchKeyEvent;
import cn.timeface.tfbox.managers.listeners.IEventBus;
import cn.timeface.tfbox.models.MovieObj;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainContentFragment extends BaseFragment implements IEventBus {


    @InjectView(R.id.tvTitle)
    TextView mTvTitle;
    @InjectView(R.id.textClock)
    TextClock mTextClock;
    @InjectView(R.id.rlTop)
    RelativeLayout mRlTop;
    @InjectView(R.id.gvContent)
    GridView mGvContent;

    MainContentAdapter mAdapter;

    public MainContentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_content, container, false);
        ButterKnife.inject(this, view);

        mGvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieObj movieObj = (MovieObj) view.getTag(R.string.tag_obj);
                DetailActivity.open(getActivity(), movieObj);
            }
        });
        return view;
    }


    @Override
    public void onEvent(Object event) {

    }

    public void onEvent(ChangeTypeEvent event) {
        mGvContent.setNumColumns(5);
        mTvTitle.setText(event.getTypeName());
        loadDataByType(event.getTypeName());
    }

    private void loadDataByType(String type) {
        if (type.equals("全部")) {
            loadData(MovieObj.getAll());
        }else if (type.equals("收藏夹")) {
            loadData(MovieObj.getAllFromCollect());
        } else {
            loadData(MovieObj.getAllFromType(type));
        }
    }

    public void onEvent(SearchKeyEvent event) {
        mGvContent.setNumColumns(4);
        if (TextUtils.isEmpty(event.getKey())) {
            mTvTitle.setText("热门");
        } else {
            mTvTitle.setText(event.getKey());
        }
        loadDataBySearchKey(event.getKey());
    }

    private void loadDataBySearchKey(String key) {
        loadData(MovieObj.getAllFromSearchKey(key));
    }

    private void loadData(List<MovieObj> content) {
        if (mAdapter == null) {
            mAdapter = new MainContentAdapter(getActivity(), content);
            mGvContent.setAdapter(mAdapter);
        } else {
            mAdapter.setListData(content);
        }
        mAdapter.notifyDataSetChanged();
    }
}