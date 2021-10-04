package com.example.androidmobilebootcampfifthweek.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.androidmobilebootcampfifthweek.R
import com.example.androidmobilebootcampfifthweek.utils.BASE_IMAGE_PATH

object ImageBindingAdapter {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setUrl(imageView: ImageView, imageUrl : String?){
        Glide.with(imageView.context)
            .load(BASE_IMAGE_PATH+"$imageUrl")
            .error(R.drawable.ic_image_not_found)
            .into(imageView)
    }
}
