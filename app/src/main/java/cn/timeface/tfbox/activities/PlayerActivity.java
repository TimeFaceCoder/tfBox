package cn.timeface.tfbox.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.baidu.cyberplayer.core.BVideoView;

import cn.timeface.tfbox.R;
import cn.timeface.tfbox.bases.BaseActivity;
import cn.timeface.tfbox.models.MovieObj;

public class PlayerActivity extends BaseActivity  implements BVideoView.OnPreparedListener, BVideoView.OnCompletionListener,
        BVideoView.OnErrorListener, BVideoView.OnInfoListener, BVideoView.OnPlayingBufferCacheListener {
    MovieObj movieObj;

    public static void open(Context context, MovieObj movieObj) {
        Intent intent = new Intent(context, PlayerActivity.class);
        intent.putExtra("movie_info", movieObj);
        context.startActivity(intent);
    }

    private final String TAG = "player";

    private String AK = "idrdG1gA6SqtnPWePbZNG8tH";
    private String SK = "LqSmKYL2sZPaIKBsaXKe6F2sDvnv1UW0";

    private String mVideoSource = null;

    private ImageButton mPlaybtn = null;
    private LinearLayout mController = null;

    private SeekBar mProgress = null;
    private TextView mDuration = null;
    private TextView mCurrPostion = null;

    /**
     * 记录播放位置
     */
    private int mLastPos = 0;

    /**
     * 播放状态
     */
    private  enum PLAYER_STATUS {
        PLAYER_IDLE, PLAYER_PREPARING, PLAYER_PREPARED,
    }

    private PLAYER_STATUS mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;

    private BVideoView mVV = null;

    private EventHandler mEventHandler;
    private HandlerThread mHandlerThread;

    private final Object SYNC_Playing = new Object();

    private PowerManager.WakeLock mWakeLock = null;
    private static final String POWER_LOCK = "VideoViewPlayingActivity";

    private boolean mIsHwDecode = false;

    private final int EVENT_PLAY = 0;
    private final int UI_EVENT_UPDATE_CURRPOSITION = 1;

    class EventHandler extends Handler {
        public EventHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_PLAY:
                    /**
                     * 如果已经播放了，等待上一次播放结束
                     */
                    if (mPlayerStatus != PLAYER_STATUS.PLAYER_IDLE) {
                        synchronized (SYNC_Playing) {
                            try {
                                SYNC_Playing.wait();
                                Log.v(TAG, "wait player status to idle");
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }

                    /**
                     * 设置播放url
                     */
                    mVV.setVideoPath(mVideoSource);

                    /**
                     * 续播，如果需要如此
                     */
                    if (mLastPos > 0) {

                        mVV.seekTo(mLastPos);
                        mLastPos = 0;
                    }

                    /**
                     * 显示或者隐藏缓冲提示
                     */
                    mVV.showCacheInfo(true);

                    /**
                     * 开始播放
                     */
                    mVV.start();

                    mPlayerStatus = PLAYER_STATUS.PLAYER_PREPARING;
                    break;
                default:
                    break;
            }
        }
    }

    Handler mUIHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                /**
                 * 更新进度及时间
                 */
                case UI_EVENT_UPDATE_CURRPOSITION:
                    int currPosition = mVV.getCurrentPosition();
                    int duration = mVV.getDuration();
                    updateTextViewWithTimeFormat(mCurrPostion, currPosition);
                    updateTextViewWithTimeFormat(mDuration, duration);
                    mProgress.setMax(duration);
                    mProgress.setProgress(currPosition);

                    mUIHandler.sendEmptyMessageDelayed(UI_EVENT_UPDATE_CURRPOSITION, 200);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_player);

        this.movieObj = (MovieObj) getIntent().getSerializableExtra("movie_info");

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, POWER_LOCK);

        mIsHwDecode = getIntent().getBooleanExtra("isHW", false);
        mVideoSource = movieObj.getMoviePath();

        initUI();

        /**
         * 开启后台事件处理线程
         */
        mHandlerThread = new HandlerThread("event handler thread",
                android.os.Process.THREAD_PRIORITY_BACKGROUND);
        mHandlerThread.start();
        mEventHandler = new EventHandler(mHandlerThread.getLooper());

    }

    /**
     * 初始化界面
     */
    private void initUI() {
        mPlaybtn = (ImageButton)findViewById(R.id.play_btn);

        mProgress = (SeekBar)findViewById(R.id.media_progress);
        mDuration = (TextView)findViewById(R.id.time_total);
        mCurrPostion = (TextView)findViewById(R.id.time_current);

        registerCallbackForControl();

        /**
         * 设置ak及sk的前16位
         */
        BVideoView.setAKSK(AK, SK);

        /**
         *获取BVideoView对象
         */
        mVV = (BVideoView) findViewById(R.id.video_view);

        /**
         * 注册listener
         */
        mVV.setOnPreparedListener(this);
        mVV.setOnCompletionListener(this);
        mVV.setOnErrorListener(this);
        mVV.setOnInfoListener(this);

        /**
         * 设置解码模式
         */
        mVV.setDecodeMode(mIsHwDecode?BVideoView.DECODE_HW:BVideoView.DECODE_SW);
    }

    /**
     * 为控件注册回调处理函数
     */
    private void registerCallbackForControl(){
        mPlaybtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (mVV.isPlaying()) {
                    mPlaybtn.setImageResource(R.drawable.play_btn_style);
                    /**
                     * 暂停播放
                     */
                    mVV.pause();
                } else {
                    mPlaybtn.setImageResource(R.drawable.pause_btn_style);
                    /**
                     * 继续播放
                     */
                    mVV.resume();
                }

            }
        });

        SeekBar.OnSeekBarChangeListener osbc1 = new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                //Log.v(TAG, "progress: " + progress);
                updateTextViewWithTimeFormat(mCurrPostion, progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                /**
                 * SeekBar开始seek时停止更新
                 */
                mUIHandler.removeMessages(UI_EVENT_UPDATE_CURRPOSITION);
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                int iseekPos = seekBar.getProgress();
                /**
                 * SeekBark完成seek时执行seekTo操作并更新界面
                 *
                 */
                mVV.seekTo(iseekPos);
                Log.v(TAG, "seek to " + iseekPos);
                mUIHandler.sendEmptyMessage(UI_EVENT_UPDATE_CURRPOSITION);
            }
        };
        mProgress.setOnSeekBarChangeListener(osbc1);
    }

    private void updateTextViewWithTimeFormat(TextView view, int second){
        int hh = second / 3600;
        int mm = second % 3600 / 60;
        int ss = second % 60;
        String strTemp = null;
        if (0 != hh) {
            strTemp = String.format("%02d:%02d:%02d", hh, mm, ss);
        } else {
            strTemp = String.format("%02d:%02d", mm, ss);
        }
        view.setText(strTemp);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        /**
         * 在停止播放前 你可以先记录当前播放的位置,以便以后可以续播
         */
        if (mPlayerStatus == PLAYER_STATUS.PLAYER_PREPARED) {
            mLastPos = mVV.getCurrentPosition();
            mVV.stopPlayback();
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.v(TAG, "onResume");
        if (null != mWakeLock && (!mWakeLock.isHeld())) {
            mWakeLock.acquire();
        }
        /**
         * 发起一次播放任务,当然您不一定要在这发起
         */
        mEventHandler.sendEmptyMessage(EVENT_PLAY);
    }

    private long mTouchTime;
    private boolean barShow = true;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            mTouchTime = System.currentTimeMillis();
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            long time = System.currentTimeMillis() - mTouchTime;
            if (time < 400) {
                updateControlBar(!barShow);
            }
        }

        return true;
    }

    public void updateControlBar(boolean show) {

        if (show) {
            mController.setVisibility(View.VISIBLE);
        } else {
            mController.setVisibility(View.INVISIBLE);
        }
        barShow = show;
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        /**
         * 退出后台事件处理线程
         */
        mHandlerThread.quit();
    }

    @Override
    public boolean onInfo(int what, int extra) {
        // TODO Auto-generated method stub
        switch(what){
            /**
             * 开始缓冲
             */
            case BVideoView.MEDIA_INFO_BUFFERING_START:
                break;
            /**
             * 结束缓冲
             */
            case BVideoView.MEDIA_INFO_BUFFERING_END:
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 当前缓冲的百分比， 可以配合onInfo中的开始缓冲和结束缓冲来显示百分比到界面
     */
    @Override
    public void onPlayingBufferCache(int percent) {
        // TODO Auto-generated method stub

    }

    /**
     * 播放出错
     */
    @Override
    public boolean onError(int what, int extra) {
        // TODO Auto-generated method stub
        Log.v(TAG, "onError");
        synchronized (SYNC_Playing) {
            SYNC_Playing.notify();
        }
        mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;
        mUIHandler.removeMessages(UI_EVENT_UPDATE_CURRPOSITION);
        return true;
    }

    /**
     * 播放完成
     */
    @Override
    public void onCompletion() {
        // TODO Auto-generated method stub
        Log.v(TAG, "onCompletion");
        synchronized (SYNC_Playing) {
            SYNC_Playing.notify();
        }
        mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;
        mUIHandler.removeMessages(UI_EVENT_UPDATE_CURRPOSITION);
    }

    /**
     * 准备播放就绪
     */
    @Override
    public void onPrepared() {
        // TODO Auto-generated method stub
        Log.v(TAG, "onPrepared");
        mPlayerStatus = PLAYER_STATUS.PLAYER_PREPARED;
        mUIHandler.sendEmptyMessage(UI_EVENT_UPDATE_CURRPOSITION);
    }
}
