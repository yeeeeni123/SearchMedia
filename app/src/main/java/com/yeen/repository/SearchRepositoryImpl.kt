package com.yeen.repository

import com.yeen.SearchService
import com.yeen.model.ListItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchRepositoryImpl(private val service: SearchService) : SearchRepository {

    override fun search(query: String): Observable<List<ListItem>> {
        return service.searchImage(query)
            .zipWith(service.searchVideo(query)) { imageResult, videoResult ->
                mutableListOf<ListItem>().apply {
                    addAll(imageResult.documents)
                    addAll(videoResult.documents)
                }.sortedBy { it.dateTime }
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }
}