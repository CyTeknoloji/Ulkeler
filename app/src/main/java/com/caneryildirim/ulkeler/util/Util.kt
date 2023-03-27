package com.caneryildirim.ulkeler.util

import android.content.Context
import android.graphics.Color
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.squareup.picasso.Picasso

fun ImageView.downloadUrl(url:String){
    Picasso.get().load(url)
        .placeholder(placeHolderProgressBar(context))
        .into(this)
}

fun placeHolderProgressBar(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        setColorSchemeColors(Color.CYAN,)
        centerRadius=40f
        strokeWidth=8f
        start()
    }
}