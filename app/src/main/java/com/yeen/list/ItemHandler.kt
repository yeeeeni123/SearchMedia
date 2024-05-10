package com.yeen.list

import com.yeen.model.ListItem

interface ItemHandler {
    fun onClickFavorite(item : ListItem)
}