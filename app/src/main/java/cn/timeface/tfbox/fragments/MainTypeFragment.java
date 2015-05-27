package cn.timeface.tfbox.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.timeface.tfbox.R;
import cn.timeface.tfbox.activities.SearchActivity;
import cn.timeface.tfbox.adapters.MainContentAdapter;
import cn.timeface.tfbox.adapters.MainTypeAdapter;
import cn.timeface.tfbox.bases.BaseFragment;
import cn.timeface.tfbox.events.ChangeTypeEvent;
import cn.timeface.tfbox.models.TypeObj;
import de.greenrobot.event.EventBus;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainTypeFragment extends BaseFragment {
    @InjectView(R.id.tvTitle)
    TextView mTvTitle;
    @InjectView(R.id.listView)
    ListView mListView;

    MainTypeAdapter mAdapter;
    List<String> types = new ArrayList<>(10);

    public MainTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_type, container, false);
        ButterKnife.inject(this, view);

        mTvTitle.setText("影片");

        for (TypeObj typeObj : TypeObj.getAll()) {
            types.add(typeObj.getName());
        }

        types.add(0, "全部");
        types.add(0, "收藏夹");
        types.add(0, "筛选");
        types.add(0, "搜索");

        mAdapter = new MainTypeAdapter(getActivity(), types);

        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) mAdapter.getItem(position);
                if (item.equals("搜索")) {
                    SearchActivity.open(getActivity());
                } else if (item.equals("筛选")) {
                    //do something
                } else if (item.equals("收藏夹")) {

                } else {
                    EventBus.getDefault().post(new ChangeTypeEvent(item));
                }

            }
        });
        return view;
    }

}
