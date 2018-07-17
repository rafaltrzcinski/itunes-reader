package com.rafaltrzcinski.itunesreader.di

import android.app.Application
import com.rafaltrzcinski.itunesreader.ItunesReaderApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@PerApp
@Component(modules = [
    (ActivityBindingModule::class),
    (AppModule::class),
    (TrackListModule::class),
    (AndroidSupportInjectionModule::class)])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: ItunesReaderApp)
}