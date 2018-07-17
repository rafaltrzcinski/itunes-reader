package com.rafaltrzcinski.itunesreader.di

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import dagger.Module
import dagger.Provides

@Module
open class AppModule {

    @Provides
    @PerApp
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @PerApp
    fun provideAssetsManager(context: Context): AssetManager = context.assets
}