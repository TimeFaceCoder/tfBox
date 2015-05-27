package cn.timeface.tfbox.bases;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import cn.timeface.tfbox.managers.listeners.IEventBus;
import de.greenrobot.event.EventBus;

/**
 * Created by rayboot on 15/5/22.
 */
public class BaseActivity extends AppCompatActivity {
    protected String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = ((Object) this).getClass().getSimpleName();
        if (this instanceof IEventBus) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (this instanceof IEventBus) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
