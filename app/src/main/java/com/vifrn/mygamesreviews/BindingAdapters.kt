package com.vifrn.mygamesreviews

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("thumb")
fun bindGameImage(imageView: ImageView, url : String?) {
    if(!url.isNullOrEmpty()) {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.placeholder)
            .into(imageView)
    } else {
        imageView.setImageResource(R.drawable.placeholder)
    }
}