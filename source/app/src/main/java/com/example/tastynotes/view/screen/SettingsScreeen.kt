package com.example.tastynotes.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tastynotes.R
import com.example.tastynotes.ui.theme.PrimaryText
import com.example.tastynotes.view.custom.RoundedButton
import com.example.tastynotes.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(navController: NavController) {
    val viewModel = SettingsViewModel(navController)

    Box(
        modifier = Modifier.fillMaxSize()
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
                text = viewModel.username.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryText,
            )
            HorizontalDivider(thickness = 2.dp)
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                RoundedButton(
                    text = stringResource(R.string.logout),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    viewModel.logout()
                }
            }
        }
    }
}