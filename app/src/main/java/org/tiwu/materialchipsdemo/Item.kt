package org.tiwu.materialchipsdemo

import android.graphics.Bitmap
import org.tiwu.materialchips.ImageProvider

class Item(private val name: String, private val bitmap: Bitmap?) : ImageProvider {

    override fun toString(): String = name
    override fun getBitmap(): Bitmap? = bitmap
}