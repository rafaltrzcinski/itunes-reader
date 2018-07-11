package com.rafaltrzcinski.itunesreader.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rafaltrzcinski.itunesreader.controller.ResourceController


class MainListViewModelFactory(private val resourceController: ResourceController) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            MainListViewModel(resourceController) as T
}