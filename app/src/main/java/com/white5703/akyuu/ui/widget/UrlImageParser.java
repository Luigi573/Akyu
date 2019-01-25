package com.white5703.akyuu.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.io.IOException;

/**
 * Created by cb on 2016/12/10.
 */

public class UrlImageParser {
    private Context mContext;
    private TextView mTextView;
    private int mImageSize;

    /**
     * @param textView 图文混排TextView
     * @param imageSize 图片显示高度
     */
    public UrlImageParser(TextView textView, Context context, int imageSize) {
        mTextView = textView;
        mContext = context;
        mImageSize = imageSize;
    }

    public Drawable getDrawable(String Url) {
        UrlDrawable UrlDrawable = new UrlDrawable();
        new ImageGetterAsyncTask(mContext, Url, UrlDrawable).execute(mTextView);
        return UrlDrawable;
    }

    public class ImageGetterAsyncTask extends AsyncTask<TextView, Void, Bitmap> {

        private UrlDrawable UrlDrawable;
        private Context context;
        private String source;
        private TextView textView;

        public ImageGetterAsyncTask(Context context, String source, UrlDrawable UrlDrawable) {
            this.context = context;
            this.source = source;
            this.UrlDrawable = UrlDrawable;
        }

        @Override
        protected Bitmap doInBackground(TextView... params) {
            //Log.v("Akyuu","doInBackground()");
            textView = params[0];

            //下载网络图片，以下是使用Picasso和Glide获取网络图片例子，也可以其他方式下载网络图片

            // 使用Picasso获取网络图片Bitmap
            //Log.v("Akyuu",source);
            Bitmap rtn = null;
            try {
                rtn = Picasso.with(context).load(source).get();
            } catch (IOException e) {
                Log.e("Akyuu", e.getMessage());
            }
            //Log.v("Akyuu" , String.valueOf((rtn == null)));
            return rtn;
        }

        @Override
        protected void onPostExecute(final Bitmap bitmap) {
            //Log.v("Akyuu","onPostExecute()" + bitmap.getWidth());
            try {
                //获取图片宽高比
                float ratio = bitmap.getWidth() * 1.0f / bitmap.getHeight();
                float k = 0.7f;
                Drawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
                bitmapDrawable.setBounds(0, 0, (int) (mImageSize * ratio),
                    (int) (mImageSize * k));
                //设置图片宽、高（这里传入的mImageSize为字体大小，所以，设置的高为字体大小，宽为按宽高比缩放）
                UrlDrawable.setBounds(0, 0, (int) (mImageSize * ratio * 1.2F),
                    (int) (mImageSize * k));
                UrlDrawable.drawable = bitmapDrawable;
                //两次调用invalidate才会在异步加载完图片后，刷新图文混排TextView，显示出图片
                UrlDrawable.invalidateSelf();
                textView.invalidate();
            } catch (Exception e) {
                /* Like a null bitmap, etc. */
            }
        }
    }
}
