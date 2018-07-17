package com.rafaltrzcinski.itunesreader.di

import javax.inject.Scope


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerApp

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerActivity