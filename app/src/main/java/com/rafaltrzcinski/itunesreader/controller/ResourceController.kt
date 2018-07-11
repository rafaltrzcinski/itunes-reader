package com.rafaltrzcinski.itunesreader.controller

import android.app.Activity
import android.content.res.AssetManager


class ResourceController(private val activity: Activity) {

    fun getAssets():AssetManager = activity.assets
}