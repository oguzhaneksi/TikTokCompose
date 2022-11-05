package com.example.tiktokcompose.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast
import androidx.media3.common.MediaItem
import com.example.tiktokcompose.domain.models.VideoData

fun List<VideoData>.toMediaItems(): List<MediaItem> {
    return map {
        MediaItem.fromUri(it.mediaUri)
    }
}

fun showToast(context: Context, message: String?) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}