package com.example.sqldelight.model

import kotlinx.serialization.Serializable

@Serializable
data class Transaction (val transferType: String,
                        val totalAmount: Double,
                        val description: String,
                        val quantity: String,
                        val date: String,
                        val identity: String,)