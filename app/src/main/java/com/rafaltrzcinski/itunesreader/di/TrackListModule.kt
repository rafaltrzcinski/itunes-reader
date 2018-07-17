package com.rafaltrzcinski.itunesreader.di

import android.content.res.AssetManager
import com.rafaltrzcinski.itunesreader.data.DataRepository
import com.rafaltrzcinski.itunesreader.data.LocalRepository
import com.rafaltrzcinski.itunesreader.data.RemoteRepository
import com.rafaltrzcinski.itunesreader.viewmodel.MainListViewModel
import dagger.Module
import dagger.Provides

@Module
open class TrackListModule {

    @Provides
    @PerApp
    @RemoteRepo
    internal fun provideRemoteRepository(): DataRepository = RemoteRepository()

    @Provides
    @PerApp
    @LocalRepo
    internal fun provideLocalRepository(assetsManager: AssetManager): DataRepository =
            LocalRepository(assetsManager)

    @Provides
    @PerActivity
    internal fun provideTrackListViewModel(localRepository: LocalRepository, remoteRepository: RemoteRepository): MainListViewModel =
            MainListViewModel(localRepository, remoteRepository)
}