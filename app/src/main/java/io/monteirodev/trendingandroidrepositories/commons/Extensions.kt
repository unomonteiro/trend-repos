package io.monteirodev.trendingandroidrepositories.commons

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import io.monteirodev.trendingandroidrepositories.R

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun ImageView.loadImg(image: String) {
    if (TextUtils.isEmpty(image)) {
        Glide.with(context).load(R.drawable.ic_github_box).into(this)
    } else {
        Glide.with(context).load(image).into(this)
    }
}