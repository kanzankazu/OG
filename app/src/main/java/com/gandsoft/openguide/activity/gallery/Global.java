package com.gandsoft.openguide.activity.gallery;

import android.content.Context;
import android.os.Environment;

import com.gandsoft.openguide.activity.gallery.images.ImageSource;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by clifford on 16/3/8.
 */
public class Global {

    private static String urls="https://bbs.qn.img-space.com/201803/1/a3d12f4355dde74483e323022866c27c.jpg";
    private static String root=Environment.getExternalStorageDirectory().toString()+"/.Gandsoft/images.jpg";

    public static String url(){
        String url = null;
        if(downloadFile(urls, new File(root))){
            url = "https://bbs.qn.img-space.com/201803/1/a3d12f4355dde74483e323022866c27c.jpg";
        }
        else{
            url = Environment.getExternalStorageDirectory().toString()+"/.Gandsoft/images.jpg";
        }
        return url;
    }

    private static ImageSource[] sTestImages = new ImageSource[] {
             new ImageSource(url(), 5760, 3840)
    };

    public static ImageSource getTestImage(int i) {
        if (i >= 0 && i < sTestImages.length) {
            return sTestImages[i];
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

    private static boolean downloadFile(String url, File outputFile) {
        boolean success;
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());

            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();

            DataOutputStream fos = new DataOutputStream(new FileOutputStream(outputFile));
            fos.write(buffer);
            fos.flush();
            fos.close();
            success=true;

        } catch(FileNotFoundException e) {
            success=false;
            return success; // swallow a 404
        } catch (IOException e) {
            success=false;
            return success; // swallow a 404
        }
        return success;
    }
}