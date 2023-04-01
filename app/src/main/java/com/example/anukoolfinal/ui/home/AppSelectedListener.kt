package com.example.anukoolfinal.ui.home

import android.content.pm.ResolveInfo
import com.example.anukoolfinal.model.AppData

sealed interface AppSelectedListener {
    fun onAppClicked(appInfo: AppData, clicked: Boolean)
}