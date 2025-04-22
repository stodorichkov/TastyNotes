package com.example.tastynotes.view.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.tastynotes.model.Screen
import com.example.tastynotes.ui.theme.Background
import com.example.tastynotes.ui.theme.Primary
import com.example.tastynotes.viewmodel.HomeViewModel
import com.example.tastynotes.viewmodel.RecipesViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = HomeViewModel(navController)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("")},
                modifier = Modifier.statusBarsPadding(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background
                )
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.navigationBarsPadding(),
                containerColor = MaterialTheme.colorScheme.surface,
            ) {
                viewModel.navItems.forEachIndexed { index, navItem ->
                    if (index == 2) {
                        IconButton(
                            modifier = Modifier.clip(CircleShape)
                                .background(Primary),

                            onClick = { navController.navigate(Screen.RecipeForm.route) }
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add",
                                tint = Color.White
                            )
                        }
                    } else {
                        NavigationBarItem(
                            selected = viewModel.selectedItem == index,
                            onClick = { viewModel.selectedItem = index},
                            icon = {
                                Icon(imageVector = navItem.icon, contentDescription = null)
                            },
                            label = {
                                Text(navItem.label)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Primary,
                                selectedTextColor = Primary,
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Background)
            ) {
                when (viewModel.selectedItem) {
                    0 -> RecipesScreen(RecipesViewModel(navController))
                    1 -> RecipesScreen(RecipesViewModel(navController))
                    3 -> RecipesScreen(RecipesViewModel(navController, true))
                    4 -> SettingsScreen(navController)
                    else -> Text("Empty tab", modifier = Modifier.padding(padding))
                }
            }
        }
    )
}