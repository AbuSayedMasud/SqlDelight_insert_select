package com.example.sqldelight.data

import android.util.Log
import com.example.sqldelight.model.Transaction
import kotlinx.coroutines.flow.Flow
import test.appdb.AppTransaction


interface appDataSource{
    fun getAppTransactions(): Flow<List<AppTransaction>>
    suspend fun insertAppTransactions(transactions: List<Transaction>)
}