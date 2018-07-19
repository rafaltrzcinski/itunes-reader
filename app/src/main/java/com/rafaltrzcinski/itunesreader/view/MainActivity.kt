package com.rafaltrzcinski.itunesreader.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.jakewharton.rxbinding2.widget.RxTextView
import com.rafaltrzcinski.itunesreader.R
import com.rafaltrzcinski.itunesreader.domain.state.DataSource
import com.rafaltrzcinski.itunesreader.domain.state.DataSource.LOCAL
import com.rafaltrzcinski.itunesreader.domain.state.DataSource.REMOTE
import com.rafaltrzcinski.itunesreader.domain.state.TrackListState
import com.rafaltrzcinski.itunesreader.viewmodel.TrackListViewModelFactory
import com.rafaltrzcinski.itunesreader.viewmodel.TrackListViewModel
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject lateinit var trackListViewModelFactory: TrackListViewModelFactory
    private val tracksAdapter: TrackListAdapter by lazy { TrackListAdapter() }
    private val trackListViewModel: TrackListViewModel by lazy {
        ViewModelProviders
                .of(this, trackListViewModelFactory)
                .get(TrackListViewModel::class.java)
    }
    private val listToLoad: MutableList<DataSource> = mutableListOf(LOCAL)

    private val compositeDisposable = CompositeDisposable()

    private val searchIntent: Observable<String> by lazy {
        RxTextView.textChangeEvents(searchField)
                .debounce(500, TimeUnit.MILLISECONDS)
                .skip(1)
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.text().toString() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        setContentView(R.layout.activity_main)

        initRecyclerView()
        observeOnList()

        compositeDisposable.add(
                searchIntent.subscribe { invalidateList(it) }
        )
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            it.isChecked = it.isChecked.not()
            when (it.itemId) {
                R.id.actionLocal ->
                    if (it.isChecked) listToLoad.add(LOCAL)
                    else listToLoad.remove(LOCAL)
                R.id.actionRemote ->
                    if (it.isChecked) listToLoad.add(REMOTE)
                    else listToLoad.remove(REMOTE)
            }
            invalidateList()
        }
        return true
    }

    private fun invalidateList(query: String = searchField.text.toString()) {
        tracksAdapter.clearItems()
        trackListViewModel.reloadTracks(query)
    }

    private fun initRecyclerView() {
        mainList.apply {
            adapter = tracksAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun observeOnList() = trackListViewModel.stateLiveData.observe(this, trackListObserver)

    private val trackListObserver: Observer<TrackListState> =
            Observer {
                it?.let {
                    when (it) {
                        is TrackListState.Error ->
                            showConnectionError(it.error?.message
                                    ?: getString(R.string.connection_error_instruction))
                        is TrackListState.Loaded -> tracksAdapter.loadItems(it.trackList)
                    }
                }
            }

    private fun showConnectionError(errorMessage: String) {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.connection_error))
            setMessage(errorMessage)
            setPositiveButton(getString(R.string.action_retry)) { _, _ -> trackListViewModel.reloadTracks() }
            setNegativeButton(getString(R.string.action_cancel)) { dialog, _ -> dialog.dismiss() }
        }.show()
    }
}
