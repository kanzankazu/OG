package com.gandsoft.openguide.activity.gallery;

import android.content.Context;

import com.gandsoft.openguide.activity.gallery.images.FengNiaoImageSource;
import com.gandsoft.openguide.activity.gallery.images.ImageSource;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by clifford on 16/3/8.
 */
public class Global {

    private static ImageSource[] sTestImages = new ImageSource[] {
             new FengNiaoImageSource("http://jonvilma.com/images/xenoblade-chronicles-x-4k.jpg", 5760, 3840)
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
}