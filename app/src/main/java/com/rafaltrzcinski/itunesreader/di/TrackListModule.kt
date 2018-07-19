package com.rafaltrzcinski.itunesreader.di

import android.content.res.AssetManager
import com.rafaltrzcinski.itunesreader.data.DataRepository
import com.rafaltrzcinski.itunesreader.data.LocalRepository
import com.rafaltrzcinski.itunesreader.data.RemoteRepository
import com.rafaltrzcinski.itunesreader.usecase.GetTrackList
import com.rafaltrzcinski.itunesreader.viewmodel.TrackListViewModel
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
    @PerApp
    internal fun provideTrackListUseCase(localRepository: LocalRepository, remoteRepository: RemoteRepository): GetTrackList =
            GetTrackList(localRepository, remoteRepository)

    @Provides
    @PerActivity
    internal fun provideTrackListViewModel(trackListUseCase: GetTrackList): TrackListViewModel =
            TrackListViewModel(trackListUseCase)
}