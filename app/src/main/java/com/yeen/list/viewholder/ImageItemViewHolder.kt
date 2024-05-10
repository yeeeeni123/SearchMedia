package com.yeen.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.yeen.list.ItemHandler
import com.yeen.medialist.databinding.ItemImageBinding
import com.yeen.model.ImageItem
import com.yeen.model.ListItem

class ImageItemViewHolder(
    private val binding: ItemImageBinding,
    private val itemHandler: ItemHandler? = null
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ListItem) {
        item as ImageItem
        binding.item = item
        binding.handler = itemHandler
    }
}