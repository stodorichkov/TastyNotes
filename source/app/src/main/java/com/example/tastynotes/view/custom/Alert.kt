package com.example.tastynotes.view.custom

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.tastynotes.R
import com.example.tastynotes.service.AlertManager


@Composable
fun Alert() {
    if (AlertManager.isShown) {
        AlertDialog(
            onDismissRequest = { null },
            title = { Text(AlertManager.title) },
            text = { Text(AlertManager.message) },
            confirmButton = {
                TextButton(onClick = { AlertManager.dismiss() }) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = null,
            shape = MaterialTheme.shapes.medium,
            containerColor = MaterialTheme.colorScheme.surface
        )
    }
}

