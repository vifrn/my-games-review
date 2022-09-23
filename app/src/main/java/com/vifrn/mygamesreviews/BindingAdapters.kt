package com.vifrn.mygamesreviews

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("imageSrc")
fun bindGameImage(imageView: ImageView, url : String?) {
    if(!url.isNullOrEmpty()) {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error_image_not_found)
            .into(imageView)
    } else {
        imageView.setImageResource(R.drawable.error_image_not_found)
    }
}

@BindingAdapter("rating")
fun bindGameRating(textView: TextView, rating : Float?) {
    textView.text = when (rating) {
        null -> "0.0"
        else -> String.format("%.2f", rating)
    }
}