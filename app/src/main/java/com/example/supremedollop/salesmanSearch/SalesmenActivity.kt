package com.example.supremedollop.salesmanSearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalesmenActivity : ComponentActivity() {
    private val viewModel: SalesmanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    @Composable
    fun MainScreen(){
        val salesmanViewData by viewModel.salesmenResults.collectAsState()
        SalesmanSearchScreen(salesmanViewData, viewModel::updateSearchQuery)
    }
}