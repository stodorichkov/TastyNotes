package com.example.tastynotes.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tastynotes.R
import com.example.tastynotes.model.Screen
import com.example.tastynotes.ui.theme.Background
import com.example.tastynotes.ui.theme.GrayText
import com.example.tastynotes.ui.theme.LogoText
import com.example.tastynotes.ui.theme.PrimaryText
import com.example.tastynotes.ui.theme.TastyNotesTheme
import com.example.tastynotes.view.custom.RoundedButton

@Composable
fun WelcomeScreen(navController: NavController) {
    Scaffold(
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Background)
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 150.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                        .size(230.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                    )
                    Text(
                        text = stringResource(R.string.app_name),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = LogoText,
                    )
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 16.dp, vertical = 30.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.welcome_title),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText,
                    )
                    Text(
                        text = stringResource(R.string.welcome_info),
                        fontSize = 16.sp,
                        color = GrayText,
                        modifier = Modifier
                            .padding(top = 4.dp, bottom = 24.dp)
                    )
                    RoundedButton(
                        text = stringResource(R.string.login),
                        color = MaterialTheme.colorScheme.primary
                    ) {
                        navController.navigate(Screen.Login.route)
                    }
                    RoundedButton(
                        text = stringResource(R.string.register),
                        color = MaterialTheme.colorScheme.secondary
                    ) {
                        navController.navigate(Screen.Registration.route)
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    val navController = rememberNavController()
    TastyNotesTheme {
        WelcomeScreen(navController = navController)
    }
}