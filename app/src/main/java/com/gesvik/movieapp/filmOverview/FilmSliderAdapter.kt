package com.gesvik.movieapp.filmOverview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gesvik.movieapp.databinding.FilmItemBinding
import com.gesvik.movieapp.network.entities.FilmsItem

class FilmSliderAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<FilmsItem, FilmSliderAdapter.FilmsItemViewHolder>(DiffCallback) {

    class FilmsItemViewHolder(private var binding: FilmItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(filmsResponse: FilmsItem) {
            binding.property = filmsResponse
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<FilmsItem>() {
        override fun areItemsTheSame(oldItem: FilmsItem, newItem: FilmsItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: FilmsItem, newItem: FilmsItem): Boolean {
            return oldItem.nameRu == newItem.nameRu
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsItemViewHolder {
        val itemBinding = FilmItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FilmsItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FilmsItemViewHolder, position: Int) {
        val filmsResponse = getItem(position)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(filmsResponse)
        }

        holder.bind(filmsResponse)
    }

    class OnClickListener(val clickListener: (filmsProperty: FilmsItem) -> Unit) {
        fun onClick(filmProperty: FilmsItem) = clickListener(filmProperty)
    }

}