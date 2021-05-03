package com.gesvik.movieapp.detailFilm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gesvik.movieapp.databinding.CastItemBinding
import com.gesvik.movieapp.network.entities.StaffProperty

class StaffAdapter :
    ListAdapter<StaffProperty, StaffAdapter.StaffViewHolder>(DiffCallback) {

    class StaffViewHolder(private var binding: CastItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(staffResponse: StaffProperty) {
            binding.property = staffResponse
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<StaffProperty>() {
        override fun areItemsTheSame(oldItem: StaffProperty, newItem: StaffProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: StaffProperty, newItem: StaffProperty): Boolean {
            return oldItem.staffId == newItem.staffId
        }
    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        val filmsResponse = getItem(position)

        holder.bind(filmsResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        val itemBinding = CastItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StaffViewHolder(itemBinding)
    }
}