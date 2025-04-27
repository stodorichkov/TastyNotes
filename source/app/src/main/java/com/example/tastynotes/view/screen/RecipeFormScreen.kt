package com.example.tastynotes.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tastynotes.model.Step
import com.example.tastynotes.ui.theme.Background
import com.example.tastynotes.ui.theme.GrayText
import com.example.tastynotes.ui.theme.Primary
import com.example.tastynotes.ui.theme.PrimaryText
import com.example.tastynotes.view.custom.RoundedButton
import com.example.tastynotes.viewmodel.RecipeFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeForm(viewModel: RecipeFormViewModel) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("")},
                navigationIcon = {
                    IconButton(onClick = { viewModel.navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                modifier = Modifier.statusBarsPadding(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background
                )
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Background)
                    .imePadding()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        focusManager.clearFocus()
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .navigationBarsPadding()
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 16.dp, vertical = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Add Recipe",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText,
                    )
                    HorizontalDivider(thickness = 2.dp)
                    TabRow(
                        selectedTabIndex = viewModel.selectedItem,
                    ) {
                        viewModel.navItems.forEachIndexed { index, item ->
                            Tab(
                                selected = viewModel.selectedItem == index,
                                onClick = { viewModel.selectedItem = index },
                                text = { Text(text = item.label) },
                                selectedContentColor = Primary,
                                unselectedContentColor = GrayText
                            )
                        }
                    }
                    when (viewModel.selectedItem) {
                        0 -> RecipeFormDetail(viewModel)
                        1 -> RecipeFormIngredients(viewModel)
                        2 -> RecipeFormSteps(viewModel)
                        else -> "Empty tab"
                    }
                }
            }
        }
    )
}

@Composable
fun RecipeFormDetail(viewModel: RecipeFormViewModel) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        RoundedButton(
            text = "Add recipe",
            color = MaterialTheme.colorScheme.primary
        ) {

        }
    }
}

@Composable
fun RecipeFormSteps(viewModel: RecipeFormViewModel) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        viewModel.steps.forEachIndexed { index, step ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = step.text,
                    onValueChange = {
                        viewModel.steps[index] = step.copy(text = it)
                    },
                    label = { Text("Step") },
                    singleLine = true
                )
                IconButton(onClick = {
                    viewModel.steps.removeAt(index)
                }) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Back",
                        tint = Color.Red
                    )
                }
            }
        }
        RoundedButton(
            text = "Add step",
            color = MaterialTheme.colorScheme.secondary
        ) {
            viewModel.steps.add(Step(text = ""))
        }
        RoundedButton(
            text = "Add recipe",
            color = MaterialTheme.colorScheme.primary
        ) {

        }
    }
}

@Composable
fun RecipeFormIngredients(viewModel: RecipeFormViewModel) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        viewModel.steps.forEachIndexed { index, step ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = step.text,
                    onValueChange = {
                        viewModel.steps[index] = step.copy(text = it)
                    },
                    label = { Text("Step") },
                    singleLine = true
                )
                IconButton(onClick = {
                    viewModel.steps.removeAt(index)
                }) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Back",
                        tint = Color.Red
                    )
                }
            }
        }
        RoundedButton(
            text = "Add step",
            color = MaterialTheme.colorScheme.secondary
        ) {
            viewModel.steps.add(Step(text = ""))
        }
        RoundedButton(
            text = "Add recipe",
            color = MaterialTheme.colorScheme.primary
        ) {

        }
    }
}