package com.test.teleprompter.presentation.features.scenarios.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun AlertDialogEnterScenario(
    onDismissRequest: () -> Unit,
    onAdd: (text: String) -> Unit
) {
    var text by rememberSaveable {
        mutableStateOf("")
    }

    AlertDialog(
        title = {
            Text(text = "Add scenario")
        },
        text = {
            TextField(
                value = text,
                onValueChange = { value ->
                    text = value
                }
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAdd(text)
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}