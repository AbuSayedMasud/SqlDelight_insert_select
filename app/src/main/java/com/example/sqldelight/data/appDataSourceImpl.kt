package com.example.sqldelight.data


import android.util.Log
import com.example.sqldelight.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import test.appdb.AppTransaction

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class appDataSourceImpl(db: AppDatabase):appDataSource {
    private  val queries=db.appTableQueries
    override fun getAppTransactions(): Flow<List<AppTransaction>> {
        val transactionList = queries.getAppTransactions().executeAsList()
        return flow {
            emit(transactionList)
        }
    }

    override suspend fun insertAppTransaction(
        id:Long?,
        transferType: String?,
        totalAmount: String?,
        description: String?,
        quantity: String?,
        date: String?,
        identity: String?
    ) {
        withContext(Dispatchers.IO){
            queries.insertTransaction(id,transferType,totalAmount,description,quantity,date,identity)
            Log.d("Insertion", "Inserting data: $id, $transferType, $totalAmount, $description, $quantity, $date, $identity")
        }

    }
}