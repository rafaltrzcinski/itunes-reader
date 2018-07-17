package com.rafaltrzcinski.itunesreader.di

import com.rafaltrzcinski.itunesreader.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [MainListActivityModule::class])
    abstract fun bindMainActivity(): MainActivity
}