package com.qamar.composeecommercestore

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.qamar.composeecommercestore.Destinations.DETAILS
import com.qamar.composeecommercestore.DestinationsArgs.ID_ARG


object DestinationsArgs {
    const val ID_ARG = "productId"
}

object Destinations {
    const val HOME = "home"
    const val DETAILS = "details/${ID_ARG}"
}

class NavigationActions(private val navController: NavHostController) {

    fun navigateToHome() {
        navController.navigate(Destinations.HOME) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }

    fun navigateToDetail(taskId: String) {
        navController.navigate("$DETAILS/$taskId")
    }

}
