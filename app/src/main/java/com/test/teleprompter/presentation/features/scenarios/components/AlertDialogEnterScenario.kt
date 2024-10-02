package com.test.teleprompter.presentation.features.scenarios.components

import androidx.compose.foundation.layout.Column
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
    onAdd: (title: String, text: String) -> Unit
) {
    var title by rememberSaveable {
        mutableStateOf("")
    }
    var text by rememberSaveable {
        mutableStateOf("")
    }

    AlertDialog(
        title = {
            Text(text = "Add scenario")
        },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { value ->
                        title = value
                    },
                    label = {
                        Text(text = "title")
                    }
                )
                TextField(
                    value = text,
                    onValueChange = { value ->
                        text = value
                    },
                    label = {
                        Text(text = "text")
                    }
                )
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAdd(title, text)
                },
                enabled = title.isNotEmpty() && text.isNotEmpty()
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