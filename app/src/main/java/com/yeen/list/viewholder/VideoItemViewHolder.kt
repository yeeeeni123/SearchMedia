package com.yeen.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.yeen.list.ItemHandler
import com.yeen.medialist.databinding.ItemVideoBinding
import com.yeen.model.ListItem
import com.yeen.model.VideoItem

class VideoItemViewHolder(private val binding: ItemVideoBinding,
                          private val itemHandler: ItemHandler? = null) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ListItem) {
        item as VideoItem
        binding.item = item
        binding.handler = itemHandler
    }
}