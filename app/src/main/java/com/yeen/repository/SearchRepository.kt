package com.yeen.repository

import com.yeen.model.ListItem
import io.reactivex.rxjava3.core.Observable

interface SearchRepository {
    fun search(query: String) : Observable<List<ListItem>>
}