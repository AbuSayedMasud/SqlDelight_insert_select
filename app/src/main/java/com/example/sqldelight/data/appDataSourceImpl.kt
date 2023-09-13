package com.example.sqldelight.data


import android.util.Log
import com.example.sqldelight.AppDatabase
import com.example.sqldelight.model.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import test.appdb.AppTransaction

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class appDataSourceImpl(db: AppDatabase) : appDataSource {
    private val queries = db.appTableQueries
    override fun getAppTransactions(): Flow<List<AppTransaction>> {
        Log.d("asbfsf","fdsfgsf")
        val transactionList = queries.getAppTransactions().executeAsList()
        return flow {
            emit(transactionList)
        }
    }

    override suspend fun insertAppTransactions(transactions: List<Transaction>) {
        withContext(Dispatchers.IO) {
            transactions.forEach { transaction ->
                queries.insertTransaction(
                    transferType = transaction.transferType,
                    totalAmount = transaction.totalAmount.toString(),
                    description = transaction.description,
                    quantity = transaction.quantity,
                    date = transaction.date,
                    identity = transaction.identity
                )
            }
            Log.d("Insertion", "Inserting multiple transactions: $transactions")
        }
    }

}

