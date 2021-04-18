package com.example.retrofitglide

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.retrofitglide.network.Comic
import com.example.retrofitglide.ui.grid.MarvelApiStatus
import com.example.retrofitglide.ui.grid.PhotoGridAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Comic>?){
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?){
    imgUrl?.let{
        val imgUri = it.toUri().buildUpon().scheme("https").appendPath("/portrait_uncanny.jpg").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .transform(RoundedCorners(50))
                    .placeholder(R.drawable.loading_image)
                    .error(R.drawable.ic_baseline_grid_on_24))
            .into(imgView)
    }
}

@BindingAdapter("marvelApiStatus")
fun bindStatus(statusImageView: ImageView, status: MarvelApiStatus?) {
    when (status) {
        MarvelApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MarvelApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.connection_error)
        }
        MarvelApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}