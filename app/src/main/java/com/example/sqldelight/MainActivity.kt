package com.example.sqldelight

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.sqldelight.data.appDataSource
import com.example.sqldelight.data.appDataSourceImpl
import com.example.sqldelight.model.Transaction
import com.example.sqldelight.ui.theme.SqlDelightTheme
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.serialization.json.Json


class MainActivity : ComponentActivity() {
    private lateinit var appViewModel: AppViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sqlDriver = createSqlDriver(applicationContext)
        val appDataSource: appDataSource = appDataSourceImpl(AppDatabase(sqlDriver))
        appViewModel = ViewModelProvider(
            this,
            AppViewModelFactory(appDataSource)
        ).get(AppViewModel::class.java)
        val packageName = applicationContext.packageName
        val resourceId =
            applicationContext.resources.getIdentifier("transaction", "raw", packageName)
        val inputStream = applicationContext.resources.openRawResource(resourceId)
        val jsonContent = inputStream.bufferedReader().use { it.readText() }

        val accountInstrumentDetails = Json.decodeFromString<List<Transaction>>(jsonContent)
        appViewModel.insertTransaction(accountInstrumentDetails)
        Log.d("dfjs", appViewModel.transactionsState.value.toString())
        setContent {
            SqlDelightTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Greeting("Android")
                        TransactionList(appViewModel)
                    }
                }
            }
        }

    }

    private fun createSqlDriver(context: Context): SqlDriver {
        return AndroidSqliteDriver(
            schema = AppDatabase.Schema,
            context = context,
            name = "appDatabase.db"
        )
    }
}

@Composable
fun TransactionList(viewModel: AppViewModel) {

    val coroutineScope = rememberCoroutineScope()

    Column {
        LazyColumn {
            items(viewModel.transactionsState.value) { transaction ->
                Text("Transfer Type: ${transaction.transferType}")
                Text("Total Amount: ${transaction.totalAmount}")
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SqlDelightTheme {
        Greeting("Android")
    }
}
