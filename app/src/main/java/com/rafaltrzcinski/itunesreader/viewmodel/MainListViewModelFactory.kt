package com.rafaltrzcinski.itunesreader.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rafaltrzcinski.itunesreader.data.LocalRepository
import com.rafaltrzcinski.itunesreader.data.RemoteRepository
import javax.inject.Inject


class MainListViewModelFactory @Inject constructor(
        private val localRepository: LocalRepository,
        private val remoteRepository: RemoteRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            MainListViewModel(localRepository, remoteRepository) as T
}