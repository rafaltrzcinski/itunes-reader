package com.rafaltrzcinski.itunesreader.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import android.content.res.AssetManager
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.rafaltrzcinski.itunesreader.controller.ResourceController
import com.rafaltrzcinski.itunesreader.domain.model.Track
import com.rafaltrzcinski.itunesreader.domain.state.DataSource
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.io.InputStream


class MainListViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val inputStreamMock = mock<InputStream>()
    private val assetManagerMock = mock<AssetManager> {
        on { open(any()) } doReturn inputStreamMock
    }

    private val resourceControllerMock = mock<ResourceController> {
        on { getAssets() } doReturn assetManagerMock
    }
    private val observerMock = mock<Observer<List<Track>>>()

    private val viewModel = MainListViewModel(resourceControllerMock)

    @Test
    fun `should execute on change on observer when setting data source on viewModel`() {
        viewModel.getTrackList().observeForever(observerMock)

        viewModel.setDataSource(DataSource.LOCAL)

        verify(observerMock).onChanged(any())
    }
}