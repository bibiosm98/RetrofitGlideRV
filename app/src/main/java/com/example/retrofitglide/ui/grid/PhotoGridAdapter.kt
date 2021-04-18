package com.example.retrofitglide.ui.grid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitglide.databinding.GridViewItemBinding
import com.example.retrofitglide.network.Comic

class PhotoGridAdapter : ListAdapter<Comic, PhotoGridAdapter.ComicViewHolder>(DiffCallback){
    class ComicViewHolder(private var binding: GridViewItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(comic: Comic){
            binding.comic = comic
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Comic>() {
        override fun areItemsTheSame(oldItem: Comic, newItem: Comic): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Comic, newItem: Comic): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGridAdapter.ComicViewHolder {
        return ComicViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PhotoGridAdapter.ComicViewHolder, position: Int) {
        val comic = getItem(position)
        holder.bind(comic)
    }

}