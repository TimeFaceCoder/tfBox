package cn.timeface.tfbox.bases;

import android.os.Bundle;
import android.support.v4.app.Fragment;


import butterknife.ButterKnife;
import cn.timeface.tfbox.managers.listeners.IEventBus;
import de.greenrobot.event.EventBus;

/**
 * @author rayboot
 * @from 14/9/29 17:42
 * @TODO
 */
public class BaseFragment extends Fragment {
    protected String TAG = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = ((Object) this).getClass().getSimpleName();
        if (this instanceof IEventBus) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this instanceof IEventBus) {
            EventBus.getDefault().unregister(this);
        }
    }

}
