package com.guilherme.marvelcharacters

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.material.composethemeadapter.MdcTheme
import com.guilherme.marvelcharacters.navigation.MainNavHost
import com.guilherme.marvelcharacters.navigation.TOP_LEVEL_DESTINATION_ROUTES
import com.guilherme.marvelcharacters.navigation.TopLevelDestination

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            MainBottomBar(
                currentDestination = currentDestination,
                onNavigationItemClick = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        MainNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun MainBottomBar(
    currentDestination: NavDestination?,
    onNavigationItemClick: (String) -> Unit = {}
) {
    if (currentDestination?.route in TOP_LEVEL_DESTINATION_ROUTES) {
        BottomNavigation {
            TopLevelDestination.values().forEach { screen ->
                BottomNavigationItem(
                    icon = { Icon(imageVector = screen.icon, contentDescription = null) },
                    label = { Text(text = stringResource(screen.titleId)) },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = { onNavigationItemClick(screen.route) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainBottomBarPreview() {
    MdcTheme {
        MainBottomBar(
            currentDestination = NavDestination(navigatorName = "Main").apply {
                route = TopLevelDestination.HOME.route
            }
        )
    }
}