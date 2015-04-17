package com.tspoon.androidtoolbelt.component.fragment;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tspoon.androidtoolbelt.App;
import com.tspoon.androidtoolbelt.R;
import com.tspoon.androidtoolbelt.utils.MemoryUtils;
import com.tspoon.androidtoolbelt.view.ArcView;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import timber.log.Timber;

public class MemoryFragment extends BaseFragment {

    @InjectView(R.id.memory_total_value) TextView mTextTotal;
    @InjectView(R.id.memory_free_value) TextView mTextFree;
    //@InjectView(R.id.memory_total_value) TextView mTextCurrent;
    @InjectView(R.id.memory_low_value) TextView mTextLow;
    @InjectView(R.id.memory_arc) ArcView mArc;

    @InjectView(R.id.button_memory) Button mButtonFill;

    private MemoryUtils mMemoryUtils;
    private Handler mHandler = new Handler();

    private boolean mUpdate;

    public static MemoryFragment newInstance() {
        return new MemoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMemoryUtils = MemoryUtils.get(mActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_memory, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mArc.setTextBottom("RAM");
    }

    @Override
    public void onResume() {
        super.onResume();
        mUpdate = true;
        mHandler.post(new UpdateRunnable());
    }

    @Override
    public void onPause() {
        super.onPause();
        mUpdate = false;
    }

    @OnClick(R.id.button_memory)
    public void onClickFill() {
        if (mButtonFill.getTag() == null) {
            App.getServiceHolder().startServices(mActivity);
            mButtonFill.setText(R.string.memory_fill_stop);
            mButtonFill.setTag("started");
        } else {
            ActivityManager am = (ActivityManager) mActivity.getSystemService(Activity.ACTIVITY_SERVICE);
            List<ActivityManager.RunningServiceInfo> services = am.getRunningServices(999);
            String packageName = mActivity.getPackageName();
            for (ActivityManager.RunningServiceInfo service : services) {
                if (service.process.startsWith(packageName)) {
                    Timber.d("Killing " + service.process);
                    Process.sendSignal(service.pid, Process.SIGNAL_KILL);
                }
            }
            //App.getServiceHolder().stopServices();
            mButtonFill.setText(R.string.memory_fill_start);
            mButtonFill.setTag(null);
        }
    }

    class UpdateRunnable implements Runnable {

        @Override
        public void run() {
            if (mUpdate) {
                mMemoryUtils.update();
                mTextTotal.setText(mMemoryUtils.getRamSize() + "");
                mTextFree.setText(mMemoryUtils.getAvailableMemory() + "");
                mTextLow.setText(String.valueOf(mMemoryUtils.isLowMemory()).toUpperCase());
                mArc.setProgress(mMemoryUtils.getFreeMemoryPercentage());
                mMemoryUtils.getMemoryThreshold();

                mHandler.postDelayed(this, 1000);
            } else {
                mHandler.removeCallbacks(this);
            }
        }
    }


}
