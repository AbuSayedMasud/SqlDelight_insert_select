package com.example.sqldelight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sqldelight.data.appDataSource

class AppViewModelFactory(private val appDataSource: appDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(appDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

