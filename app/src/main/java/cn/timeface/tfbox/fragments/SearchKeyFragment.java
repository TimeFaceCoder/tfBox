package cn.timeface.tfbox.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.timeface.tfbox.R;
import cn.timeface.tfbox.adapters.SearchKeyAdapter;
import cn.timeface.tfbox.bases.BaseFragment;
import cn.timeface.tfbox.events.SearchKeyEvent;
import cn.timeface.tfbox.views.NoScrollGridView;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchKeyFragment extends BaseFragment {


    @InjectView(R.id.tvBack)
    TextView mTvBack;
    @InjectView(R.id.tvName)
    EditText mTvName;
    @InjectView(R.id.gvKeys)
    NoScrollGridView mGvKeys;
    @InjectView(R.id.btnInputBack)
    Button mBtnInputBack;
    @InjectView(R.id.btnInputClear)
    Button mBtnInputClear;

    String keys[] = {"A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X",
            "Y", "Z", "0", "1", "2", "3",
            "4", "5", "6", "7", "8", "9"};

    SearchKeyAdapter mAdapter;
    @InjectView(R.id.tvTip)
    TextView mTvTip;

    public SearchKeyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_key, container, false);
        ButterKnife.inject(this, view);

        String styledText = "拼音首字母搜索例如：《奔跑吧兄弟》输入<font color='#0069ea'>BPBXD</font>";
        mTvTip.setText(Html.fromHtml(styledText));

        mAdapter = new SearchKeyAdapter(getActivity(), Arrays.asList(keys));
        mGvKeys.setAdapter(mAdapter);
        mGvKeys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String key = (String) mAdapter.getItem(position);
                String curText = mTvName.getText().toString();
                mTvName.setText(curText + key);
                doSearch(curText + key);
            }
        });
        return view;
    }

    private void doSearch(String key) {
        EventBus.getDefault().post(new SearchKeyEvent(key));
    }

    @OnClick(R.id.btnInputBack)
    public void onInputBack(View view) {
        String curText = mTvName.getText().toString();
        if (TextUtils.isEmpty(curText)) {
            doSearch("");
            return;
        }
        curText = (String) curText.subSequence(0, curText.length() - 1);
        mTvName.setText(curText);
        doSearch(curText);
    }

    @OnClick(R.id.btnInputClear)
    public void onInputClear(View view) {
        mTvName.setText("");
        doSearch("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
