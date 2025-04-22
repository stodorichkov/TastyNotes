package com.example.tastynotes.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tastynotes.R
import com.example.tastynotes.ui.theme.Background
import com.example.tastynotes.ui.theme.PrimaryText
import com.example.tastynotes.ui.theme.TastyNotesTheme
import com.example.tastynotes.view.custom.RoundedButton
import com.example.tastynotes.viewmodel.UserFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserFormScreen(navController: NavController, isLogin: Boolean) {
    val viewModel = UserFormViewModel(
        navController,
        isLogin
    )
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val title = if (isLogin) R.string.login else R.string.register

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("")},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 16.dp, vertical = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(title),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText,
                    )
                    HorizontalDivider(thickness = 2.dp)
                    Column(
                        modifier = Modifier
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        OutlinedTextField(
                            value = viewModel.username,
                            onValueChange = { viewModel.username = it },
                            label = { Text(stringResource(R.string.username)) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        if (!isLogin) {
                            OutlinedTextField(
                                value = viewModel.email,
                                onValueChange = { viewModel.email = it },
                                label = { Text(stringResource(R.string.email)) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }
                        OutlinedTextField(
                            value = viewModel.password,
                            onValueChange = { viewModel.password = it },
                            label = { Text(stringResource(R.string.password)) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            visualTransformation = if (viewModel.passwordVisible) {
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            },
                            trailingIcon = {
                                val image = if (viewModel.passwordVisible)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff

                                val description = if (viewModel.passwordVisible) {
                                    "Hide password"
                                } else {
                                    "Show password"
                                }

                                IconButton(
                                    onClick = {
                                        viewModel.passwordVisible = !viewModel.passwordVisible
                                    }
                                ) {
                                    Icon(imageVector = image, contentDescription = description)
                                }
                            }
                        )
                        if (!isLogin) {
                            OutlinedTextField(
                                value = viewModel.confirmPassword,
                                onValueChange = { viewModel.confirmPassword = it },
                                label = { Text(stringResource(R.string.confirm)) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                visualTransformation = if (viewModel.confirmPasswordVisible) {
                                    VisualTransformation.None
                                } else {
                                    PasswordVisualTransformation()
                                },
                                trailingIcon = {
                                    val image = if (viewModel.confirmPasswordVisible)
                                        Icons.Filled.Visibility
                                    else Icons.Filled.VisibilityOff

                                    val description = if (viewModel.confirmPasswordVisible) {
                                        "Hide password"
                                    } else {
                                        "Show password"
                                    }

                                    IconButton(
                                        onClick = {
                                            viewModel.confirmPasswordVisible =
                                                !viewModel.confirmPasswordVisible
                                        }
                                    ) {
                                        Icon(imageVector = image, contentDescription = description)
                                    }
                                }
                            )
                        }
                        RoundedButton(
                            text = stringResource(title),
                            color = MaterialTheme.colorScheme.primary
                        ) {
                            viewModel.onSubmit()
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun UserFormScreenPreview() {
    val navController = rememberNavController()
    TastyNotesTheme {
        UserFormScreen(navController, false)
    }
}