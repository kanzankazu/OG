package com.gandsoft.openguide.activity.gallery.images;

import java.io.Serializable;

public interface ImageSource extends Serializable {
    ImageObject getThumb(int width, int height);
    ImageObject getOrigin();
}