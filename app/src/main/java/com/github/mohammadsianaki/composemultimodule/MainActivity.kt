package com.github.mohammadsianaki.composemultimodule

import HomeScreen
import MarketsScreen
import ProfileScreen
import TradeScreen
import WalletScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.mohammadsianaki.composemultimodule.ui.theme.ComposeMultiModuleTheme
import com.github.mohammadsianaki.navigation.BottomBarScreens
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

class MainActivity : ComponentActivity() {
    @OptIn(
        ExperimentalMaterial3Api::class,
        com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMultiModuleTheme {
                // A surface container using the 'background' color from the theme
                val bottomSheetNavigator = rememberBottomSheetNavigator()
                val navController = rememberNavController(bottomSheetNavigator)
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { AppBottomNavigation(navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = BottomBarScreens.screens.first().route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(BottomBarScreens.Home.route) {
                            HomeScreen()
                        }
                        composable(BottomBarScreens.Markets.route) {
                            MarketsScreen()
                        }
                        composable(BottomBarScreens.Trade.route) {
                            TradeScreen()
                        }
                        composable(BottomBarScreens.Wallet.route) {
                            WalletScreen()
                        }
                        composable(BottomBarScreens.Profile.route) {
                            ProfileScreen(navController)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun AppBottomNavigation(navController: NavHostController) {
        NavigationBar {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            BottomBarScreens.screens.forEachIndexed { index, screen ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                        }
                    },
                    label = {
                        Text(text = getString(screen.stringRes))
                    },
                    icon = {
                        val icon = when (screen) {
                            is BottomBarScreens.Home -> Icons.Default.Home
                            is BottomBarScreens.Markets -> Icons.Default.Place
                            is BottomBarScreens.Trade -> Icons.Default.Settings
                            is BottomBarScreens.Wallet -> Icons.Default.ShoppingCart
                            is BottomBarScreens.Profile -> Icons.Default.Person
                        }
                        Icon(icon, contentDescription = null)
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeMultiModuleTheme {
        Greeting("Android")
    }
}