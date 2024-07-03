package com.jay.stockapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jay.stockapp.holdingscreen.HoldingScreen
import com.jay.stockapp.holdingscreen.viewmodel.HoldingScreenViewModel
import com.jay.stockapp.ui.theme.StockAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            StockAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background,
                ) {
                    NavHost(navController, startDestination = "HoldingsScreen") {
                        composable("HoldingsScreen") {
                            HoldingScreen(
                                modifier = Modifier.fillMaxSize(),
                                screenViewModel = hiltViewModel<HoldingScreenViewModel>()
                            )
                        }
                    }
                }
            }
        }
    }
}
