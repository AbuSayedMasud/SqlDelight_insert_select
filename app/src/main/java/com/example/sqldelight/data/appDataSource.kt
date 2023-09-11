package com.example.sqldelight.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import test.appdb.AppTransaction


interface appDataSource{
    fun getAppTransactions(): Flow<List<AppTransaction>>
    suspend fun insertAppTransaction(id:Long? ,transferType: String?,totalAmount: String?,description: String?,quantity: String?,date: String?,identity: String?){
        Log.d("Insertion", "Inserting data: $id, $transferType, $totalAmount, $description, $quantity, $date, $identity")
    }
}