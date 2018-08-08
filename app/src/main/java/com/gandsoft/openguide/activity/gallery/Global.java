package com.gandsoft.openguide.activity.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.gandsoft.openguide.activity.gallery.images.ImageSource;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by clifford on 16/3/8.
 */
public class Global {

    private static String url="https://bbs.qn.img-space.com/201803/1/a3d12f4355dde74483e323022866c27c.jpg";
    private static String root="file:///mnt/sdcard/.Gandsoft/image.jpg";

    private static ImageSource[] sTestImages = new ImageSource[] {
             new ImageSource(url(),720,1280)
    };

    public static String url(){
        String urls;
        if(isOnline()){
            urls=url;
        }
        else{
            urls=root;
        }
        return urls;
    }

    public static ImageSource getTestImage(int i) {
        if (i >= 0 && i < sTestImages.length) {
            return sTestImages[i];
        }
        return null;
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

    public static int getTestImagesCount() {
        return sTestImages.length;
    }

    public static ImageLoader getImageLoader(Context context) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }
        return imageLoader;
    }


    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }
}