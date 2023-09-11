package com.example.sqldelight


import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import com.example.sqldelight.data.appDataSource
import kotlinx.coroutines.flow.MutableStateFlow

import test.appdb.AppTransaction

class AppViewModel(private val appDataSource: appDataSource) : ViewModel() {

    var transactionsState = MutableStateFlow<List<AppTransaction>>(emptyList())

    init {
        // Start collecting the transactions flow when the ViewModel is created
        viewModelScope.launch {
            appDataSource.getAppTransactions().collect { transactions ->
                transactionsState.value = transactions
            }
        }
    }
    fun insertTransaction(
        transferType: String?,
        totalAmount: String?,
        description: String?,
        quantity: String?,
        date: String?,
        identity: String?
    ) {
        viewModelScope.launch {
            appDataSource.insertAppTransaction(
                id = null,
                transferType,
                totalAmount,
                description,
                quantity,
                date,
                identity
            )

            transactionsState.value = appDataSource.getAppTransactions().first()
        }
    }
}
