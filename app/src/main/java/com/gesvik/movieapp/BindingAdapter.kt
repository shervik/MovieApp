package com.gesvik.movieapp

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gesvik.movieapp.detailFilm.StaffAdapter
import com.gesvik.movieapp.network.entities.FilmsItem
import com.gesvik.movieapp.network.entities.StaffProperty
import com.gesvik.movieapp.filmOverview.FilmSliderAdapter
import com.gesvik.movieapp.filmOverview.FilmsApiStatus

@BindingAdapter("listStaff")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<StaffProperty>?) {
    val adapter = recyclerView.adapter as StaffAdapter
    adapter.submitList(data)
}

@BindingAdapter("listViewPager")
fun bindViewPager(viewPager: ViewPager2, data: List<FilmsItem>?) {
    val adapter = viewPager.adapter as FilmSliderAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("filmsApiStatus")
fun bindStatus(statusImageView: ImageView, status: FilmsApiStatus?) {
    when (status) {
        FilmsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        FilmsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        FilmsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}