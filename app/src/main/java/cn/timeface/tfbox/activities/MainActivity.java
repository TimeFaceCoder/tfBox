package cn.timeface.tfbox.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.timeface.tfbox.R;
import cn.timeface.tfbox.bases.BaseActivity;


public class MainActivity extends BaseActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
