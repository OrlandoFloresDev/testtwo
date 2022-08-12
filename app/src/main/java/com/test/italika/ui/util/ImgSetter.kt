package com.test.italika.ui.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.test.italika.BuildConfig


class ImgSetter {
    companion object {
        @JvmStatic
        @BindingAdapter("android:src_image")
        fun setImageUrl(imageView: ImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                Picasso.get()
                    .load(BuildConfig.BASE_URL_IMG + BuildConfig.SMALL_SIZE + url)
                    .into(imageView);
            }
        }

        @JvmStatic
        @BindingAdapter("android:src_image_detail")
        fun setImageUrlDetail(imageView: ImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                Picasso.get()
                    .load(BuildConfig.BASE_URL_IMG + BuildConfig.ORIGINAL_SIZE + url)
                    .into(imageView);
            }
        }
    }
}