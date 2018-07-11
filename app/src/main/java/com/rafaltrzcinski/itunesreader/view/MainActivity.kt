package com.rafaltrzcinski.itunesreader.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.rafaltrzcinski.itunesreader.R
import com.rafaltrzcinski.itunesreader.controller.ResourceController
import com.rafaltrzcinski.itunesreader.domain.model.Track
import com.rafaltrzcinski.itunesreader.domain.state.DataSource
import com.rafaltrzcinski.itunesreader.domain.state.DataSource.LOCAL
import com.rafaltrzcinski.itunesreader.domain.state.DataSource.REMOTE
import com.rafaltrzcinski.itunesreader.viewmodel.MainListViewModelFactory
import com.rafaltrzcinski.itunesreader.viewmodel.MainListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val resourceController = ResourceController(this)
    private val tracksAdapter: TrackListAdapter by lazy { TrackListAdapter() }
    private val mainListViewModel: MainListViewModel by lazy {
        ViewModelProviders
                .of(this, MainListViewModelFactory(resourceController))
                .get(MainListViewModel::class.java)
    }

    private val listToLoad: MutableList<DataSource> = mutableListOf(LOCAL)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        observeOnList()
        invalidateList()
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
                else -> {}
            }
            invalidateList()
        }
        return true
    }

    private fun invalidateList() {
        tracksAdapter.clearItems()
        listToLoad.forEach {
            mainListViewModel.setDataSource(it)
        }
    }

    private fun initRecyclerView() {
        mainList.apply {
            adapter = tracksAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun observeOnList() {
        mainListViewModel.getTrackList().observe(this, mainListObserver)
    }

    private val mainListObserver: Observer<List<Track>> =
            Observer {
                it?.let { tracksAdapter.loadItems(it) } ?: showConnectionError()
            }

    private fun showConnectionError() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.connection_error))
            setMessage(getString(R.string.connection_error_instruction))
            setPositiveButton(getString(R.string.action_retry)) { _, _ -> mainListViewModel.setDataSource(REMOTE) }
            setNegativeButton(getString(R.string.action_cancel)) { dialog, _ -> dialog.dismiss() }
        }.show()
    }
}
