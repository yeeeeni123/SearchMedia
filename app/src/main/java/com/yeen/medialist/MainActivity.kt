package com.yeen.medialist

import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.yeen.FavoritesFragment
import com.yeen.SearchFragment
import com.yeen.ViewPagerAdapter
import com.yeen.medialist.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val searchFragment = SearchFragment()
    private val fragments = listOf(searchFragment, FavoritesFragment())
    private val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle, fragments)

    private var observableTextQuery : Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        observableTextQuery?.dispose()
        observableTextQuery = null
    }

    private fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            view = this@MainActivity
            viewPager.adapter = adapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = if (fragments[position] is SearchFragment) {
                    "검색 결과"
                } else {
                    "즐겨 찾기"
                }
            }.attach()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        observableTextQuery = Observable
            .create { emitter: ObservableEmitter<String>? ->
                (menu.findItem(R.id.search).actionView as SearchView).apply {
                    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String): Boolean {
                            emitter?.onNext(query)
                            return false
                        }

                        override fun onQueryTextChange(newText: String): Boolean {
                            binding.viewPager.setCurrentItem(0, true)
                            emitter?.onNext(newText)
                            return false
                        }
                    })
                }
            }
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                searchFragment.searchKeyword(it)
            }
        return true
    }
}