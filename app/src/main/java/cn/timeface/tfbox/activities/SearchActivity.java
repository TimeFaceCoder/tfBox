package cn.timeface.tfbox.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.timeface.tfbox.R;
import cn.timeface.tfbox.bases.BaseActivity;

public class SearchActivity extends BaseActivity {

    public static void open(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

}
