package org.tiwu.materialchips;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

public interface ImageProvider {

    /**
     * Returns a bitmap to be shown as a thumbnail in the corresponding Chip.
     * May return null if no thumbnail should be shown.
     *
     * @return Bitmap
     */
    @Nullable
    Bitmap getBitmap();
}
