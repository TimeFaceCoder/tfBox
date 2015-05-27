package cn.timeface.tfbox.activities;

import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.timeface.tfbox.R;
import cn.timeface.tfbox.bases.BaseActivity;
import cn.timeface.tfbox.models.MovieObj;


public class SplashActivity extends BaseActivity {
    String infoPath;
    List<MovieObj> movieObjs = new ArrayList<>(10);
    @InjectView(R.id.tvCurFile)
    TextView mTvCurFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);

        if (MovieObj.getAll() == null || MovieObj.getAll().size() == 0) {
            loadInfo();
            MainActivity.open(this);
        } else {
            MainActivity.open(this);
        }
    }

    /**
     * 目录结构为SD卡目录下直接存放视频文件以及info文件夹
     * info文件夹内包括所有已视频文件名命名的文件夹
     * 该文件夹下存储info（包括视频基本信息）photoinfo（视频图片信息）以及若干已图片url的hashcode命名的图片资源
     */
    private void loadInfo() {
        String sdcardPath = getSDPath();
        if (TextUtils.isEmpty(sdcardPath)) {
            Toast.makeText(this, "没有找到存储卡", Toast.LENGTH_SHORT).show();
            return;
        }
        File infoPath = new File(sdcardPath, "info");
        if (infoPath.exists() && infoPath.isDirectory()) {
            this.infoPath = infoPath.getAbsolutePath();
        } else {
            Toast.makeText(this, "未找到info目录", Toast.LENGTH_SHORT).show();
            return;
        }

        File[] files = new File(sdcardPath).listFiles();
        for (File file : files) {
            if (!file.isDirectory()) {
                mTvCurFile.setText(file.getAbsolutePath());
                MovieObj movieObj = new MovieObj(file.getAbsolutePath());
                if (movieObj.isSuccessLoadData()) {
                    movieObjs.add(movieObj);
                    movieObj.save();
                }
            }
        }
    }


    public String getSDPath() {
        File sdDir;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
            return sdDir.getAbsolutePath();
        }
        return null;

    }
}
