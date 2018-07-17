package com.rafaltrzcinski.itunesreader.di

import com.rafaltrzcinski.itunesreader.view.MainActivity
import dagger.Module
import dagger.Provides

@Module
open class MainListActivityModule {

    @Provides
    @PerActivity
    internal fun provideMainListActivity(mainListActivity: MainActivity): MainActivity = mainListActivity
}