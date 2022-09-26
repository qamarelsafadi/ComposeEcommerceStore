package com.qamar.composeecommercestore

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.qamar.composeecommercestore.ui.detail.DetailsScreen
import com.qamar.composeecommercestore.ui.home.HomeScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.HOME,
    navActions: NavigationActions = remember(navController) {
        NavigationActions(navController)
    }
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    //  val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(Destinations.HOME) {
            HomeScreen(onProductClick = {
                    product -> navActions.navigateToDetail(product.id.toString())
            })
        }
        composable(Destinations.DETAILS) {
            DetailsScreen(
                onBack = { navController.popBackStack() },
            )
        }
    }
}