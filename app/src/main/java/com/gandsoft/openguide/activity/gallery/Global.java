package com.gandsoft.openguide.activity.gallery;

import android.content.Context;

import com.gandsoft.openguide.activity.gallery.images.ImageSource;
import com.gandsoft.openguide.activity.gallery.images.ImageSourceInterface;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class Global {

    private static ImageSourceInterface[] sTestImages = new ImageSourceInterface[] {
             new ImageSource("http://jonvilma.com/images/xenoblade-chronicles-x-4k.jpg", 5760, 3840)
    };

    public static ImageSourceInterface getTestImage(int i) {
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