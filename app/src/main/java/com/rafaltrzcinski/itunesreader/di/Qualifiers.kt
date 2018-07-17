package com.rafaltrzcinski.itunesreader.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteRepo

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalRepo