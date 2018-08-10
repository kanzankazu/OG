package com.gandsoft.openguide.activity.infomenu.gallery.images;

import java.io.Serializable;

public interface ImageSourceInterface extends Serializable {
    ImageObject getThumb(int width, int height);
    ImageObject getOrigin();
}