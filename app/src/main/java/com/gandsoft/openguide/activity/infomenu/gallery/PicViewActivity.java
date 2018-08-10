package com.gandsoft.openguide.activity.gallery;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.ImageView;

import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.gallery.images.ImageObject;
import com.gandsoft.openguide.activity.gallery.images.ImageSourceInterface;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;


public class PicViewActivity extends Activity {

    private static final long ANIM_TIME = 0;

    private RectF mThumbMaskRect;
    private Matrix mThumbImageMatrix;

    private ObjectAnimator mBackgroundAnimator;

    private View mBackground;
    private PinchImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageSourceInterface image = (ImageSourceInterface) getIntent().getSerializableExtra("image");
        String memoryCacheKey =  getIntent().getStringExtra("cache_key");
        final Rect rect = getIntent().getParcelableExtra("rect");
        final ImageView.ScaleType scaleType = (ImageView.ScaleType) getIntent().getSerializableExtra("scaleType");
        final ImageObject thumb = image.getThumb(1000, 1000);

        ImageLoader imageLoader = Global.getImageLoader(getApplicationContext());
        DisplayImageOptions originOptions = new DisplayImageOptions.Builder().build();

        setContentView(R.layout.activity_pic_view);
        mImageView = (PinchImageView) findViewById(R.id.pic);
        mBackground = findViewById(R.id.background);
        String root = Environment.getExternalStorageDirectory().toString();

        Bitmap bitmap = imageLoader.getMemoryCache().get(memoryCacheKey);
        if(Global.isOnline()){
            saveToInternalStorage(bitmap);
        }
        mImageView.setImageBitmap(bitmap);

        imageLoader.displayImage(image.getThumb(1000, 1000).url, mImageView, originOptions);

        mImageView.post(new Runnable() {
            @Override
            public void run() {
                mImageView.setAlpha(1f);
                mBackgroundAnimator = ObjectAnimator.ofFloat(mBackground, "alpha", 0f, 1f);
                mBackgroundAnimator.setDuration(ANIM_TIME);
                mBackgroundAnimator.start();

                Rect tempRect = new Rect();
                mImageView.getGlobalVisibleRect(tempRect);
                rect.top = rect.top - tempRect.top;
                rect.bottom = rect.bottom - tempRect.top;

                mThumbMaskRect = new RectF(rect);
                RectF bigMaskRect = new RectF(0, 0, mImageView.getWidth(), mImageView.getHeight());
                mImageView.zoomMaskTo(mThumbMaskRect, 0);
                mImageView.zoomMaskTo(bigMaskRect, ANIM_TIME);

                RectF thumbImageMatrixRect = new RectF();
                PinchImageView.MathUtils.calculateScaledRectInContainer(new RectF(rect), thumb.width, thumb.height, scaleType, thumbImageMatrixRect);

                RectF bigImageMatrixRect = new RectF();
                PinchImageView.MathUtils.calculateScaledRectInContainer(new RectF(0, 0, mImageView.getWidth(), mImageView.getHeight()), thumb.width, thumb.height, ImageView.ScaleType.FIT_CENTER, bigImageMatrixRect);

                mThumbImageMatrix = new Matrix();
                PinchImageView.MathUtils.calculateRectTranslateMatrix(bigImageMatrixRect, thumbImageMatrixRect, mThumbImageMatrix);

                mImageView.outerMatrixTo(mThumbImageMatrix, 0);
                mImageView.outerMatrixTo(new Matrix(), ANIM_TIME);
            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageView.playSoundEffect(SoundEffectConstants.CLICK);
                finish();
            }
        });
    }



    @Override
    public void finish() {
        if ((mBackgroundAnimator != null && mBackgroundAnimator.isRunning())) {
            return;
        }

        mBackgroundAnimator = ObjectAnimator.ofFloat(mBackground, "alpha", mBackground.getAlpha(), 0f);
        mBackgroundAnimator.setDuration(ANIM_TIME);
        mBackgroundAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                PicViewActivity.super.finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mBackgroundAnimator.start();

        mImageView.zoomMaskTo(mThumbMaskRect, ANIM_TIME);

        mImageView.outerMatrixTo(mThumbImageMatrix, ANIM_TIME);
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/.Gandsoft/");
        myDir.mkdirs();
        String fname = "image.jpg";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}