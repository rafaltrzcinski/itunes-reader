package com.rafaltrzcinski.itunesreader.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rafaltrzcinski.itunesreader.usecase.GetTrackList
import javax.inject.Inject


class TrackListViewModelFactory @Inject constructor(
        private val trackListUseCase: GetTrackList) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            TrackListViewModel(trackListUseCase) as T
}