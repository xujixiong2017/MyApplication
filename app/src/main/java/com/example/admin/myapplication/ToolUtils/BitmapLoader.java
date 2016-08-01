package com.example.admin.myapplication.ToolUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by admin on 2016/8/1.
 */
public class BitmapLoader {

    //接口回掉事件
    public interface CallBack {
        void onRequestComplete(Bitmap bitmap);
    }

    /**
     * 异步获取网络图片
     *
     * @param imageUrl
     * @param callBack
     */
    public static void LoaderAsyn(final String imageUrl, final CallBack callBack) {

        new Thread() {
            @Override
            public void run() {
                try {
                    Bitmap bp = Loader(imageUrl);
                    if (callBack != null) {
                        callBack.onRequestComplete(bp);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 获取网络图片
     *
     * @param imageUrl
     * @return
     */
    public static Bitmap Loader(String imageUrl) {

        URL imgUrl = null;
        Bitmap bitmap = null;
        try {
            imgUrl = new URL(imageUrl);
            HttpURLConnection urlConn = (HttpURLConnection) imgUrl.openConnection();
            urlConn.setDoInput(true);
            urlConn.setReadTimeout(10000);
            urlConn.connect();
            InputStream is = urlConn.getInputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = 5;
            bitmap = BitmapFactory.decodeStream(is, null, options);
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
