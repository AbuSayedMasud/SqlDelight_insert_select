package com.example.sqldelight


import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import com.example.sqldelight.data.appDataSource
import com.example.sqldelight.model.Transaction
import kotlinx.coroutines.flow.MutableStateFlow

import test.appdb.AppTransaction

class AppViewModel(private val appDataSource: appDataSource) : ViewModel() {

    var transactionsState = MutableStateFlow<List<AppTransaction>>(emptyList())

    init {
        Log.d("MyApp", "AppViewModel initialized")
        viewModelScope.launch {
            appDataSource.getAppTransactions().collect { transactions ->
                transactionsState.value = transactions
            }
        }
    }
    fun insertTransaction(transactions: List<Transaction>) {
        Log.d("MyApp", "insertTransaction called with ${transactions.size} transactions.")
        viewModelScope.launch {
            try {
                appDataSource.insertAppTransactions(transactions)
                val updatedTransactions = appDataSource.getAppTransactions().first()
                transactionsState.value = transactionsState.value + updatedTransactions
            } catch (e: Exception) {
                Log.e("MyApp", "Error inserting transactions: ${e.message}", e)
            }
        }
    }

}

