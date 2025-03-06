package uz.shokhrukh.todolistmanager

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.shokhrukh.todolistmanager.presentation.components.ToolBar
import uz.shokhrukh.todolistmanager.presentation.navigation.Screens
import uz.shokhrukh.todolistmanager.presentation.screens.create.ToDoCreateScreen
import uz.shokhrukh.todolistmanager.presentation.screens.create.ToDoCreateViewModel
import uz.shokhrukh.todolistmanager.presentation.screens.details.ToDoDetailsViewModel
import uz.shokhrukh.todolistmanager.presentation.screens.details.ToDoiDetailsScreen
import uz.shokhrukh.todolistmanager.presentation.screens.list.ToDoListScreen
import uz.shokhrukh.todolistmanager.presentation.screens.list.ToDoListViewModel
import uz.shokhrukh.todolistmanager.presentation.screens.splash.SplashScreen
import uz.shokhrukh.todolistmanager.presentation.theme.ToDoListManagerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListManagerTheme {
                val navController = rememberNavController()
                val coroutineScope = rememberCoroutineScope()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val mapScreenTitle = "Todo List"
                Scaffold(
                    topBar = {
                        val route = currentRoute?.split(".")?.last()
                        Log.d("route", "onCreate:  $route")
                        if (route != Screens.Splash.toString()) {
                            ToolBar(
                                title = when (route) {
                                    Screens.ToDoCrate.toString() -> "New Todo"
                                    "ToDoDetails/{id}" -> "Edit Todo"
                                    else -> mapScreenTitle
                                },
                                isBack = route == Screens.ToDoCrate.toString() || route == "ToDoDetails/{id}",
                                backClick = {
                                    coroutineScope.launch {
                                        navController.popBackStack()
                                    }
                                }
                            )
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screens.Splash,
                        enterTransition = {
                            EnterTransition.None
                        },
                        exitTransition = {
                            ExitTransition.None
                        },
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<Screens.Splash> {

                            SplashScreen {
                                coroutineScope.launch {
                                    navController.navigate(Screens.ToDoList) {
                                        popUpTo(Screens.Splash) { inclusive = true }
                                    }
                                }
                            }
                        }
                        composable<Screens.ToDoList> {
                            val viewModel = hiltViewModel<ToDoListViewModel>()
                            val state = viewModel.state.collectAsStateWithLifecycle().value
                            ToDoListScreen(
                                state = state,
                                event = viewModel::onEvent,
                                onItemClick = {
                                    coroutineScope.launch {
                                        navController.navigate(Screens.ToDoDetails(it))
                                    }
                                },
                                onNavigateToCreateScreen = {
                                    coroutineScope.launch {
                                        navController.navigate(Screens.ToDoCrate)
                                    }
                                }
                            )
                        }
                        composable<Screens.ToDoCrate> {
                            val viewModel = hiltViewModel<ToDoCreateViewModel>()
                            val state = viewModel.state.collectAsStateWithLifecycle().value
                            ToDoCreateScreen(
                                state = state,
                                event = viewModel::onEvent,
                                onPopBackStack = {
                                    coroutineScope.launch {
                                        navController.popBackStack()
                                    }
                                }
                            )
                        }
                        composable<Screens.ToDoDetails> {
                            val details: Screens.ToDoDetails = it.toRoute()
                            val viewModel = hiltViewModel<ToDoDetailsViewModel>()
                            val state = viewModel.state.collectAsStateWithLifecycle().value
                            ToDoiDetailsScreen(
                                toDoId = details.id,
                                state = state,
                                event = viewModel::onEvent,
                                onPopBackStack = {
                                    coroutineScope.launch {
                                        navController.popBackStack()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
