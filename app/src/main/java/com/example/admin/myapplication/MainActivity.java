package com.example.admin.myapplication;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.admin.myapplication.ToolUtils.HttpUtils;
import com.example.admin.myapplication.ToolUtils.BitmapLoader;
import com.example.admin.myapplication.ToolUtils.PicUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt;
    public Bitmap bitmap = null;
    private ImageView im;
    private String baseUrl = "http://121.41.90.252:8081/api/1.0/student/studentinfo/studentLogin";
    private String baseImageUrl = "http://jiashi.b0.upaiyun.com/coach/avatar/11b7dabd5b1ed5c76cb4979ccd328582.jpg";
    private HttpUtils httpUtils = new HttpUtils();
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = (Button) findViewById(R.id.button);
        im = (ImageView) findViewById(R.id.image);
        bt.setOnClickListener(this);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0x00001:
                        im.setImageBitmap(PicUtils.getRoundedCornerBitmap(bitmap,50));
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                try {
                    httpUtils.doPostAsyn(baseUrl, "", callBackJson);
                    BitmapLoader.LoaderAsyn(baseImageUrl, callBackImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private BitmapLoader.CallBack callBackImage = new BitmapLoader.CallBack() {

        @Override
        public void onRequestComplete(Bitmap bp) {
            bitmap = bp;
            handler.sendEmptyMessage(0x00001);
        }
    };

    private HttpUtils.CallBack callBackJson = new HttpUtils.CallBack() {
        @Override
        public void onRequestComplete(String result) {
            Log.v("res=", result);
        }
    };
}
