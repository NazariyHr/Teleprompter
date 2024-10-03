package tele.read.adamkorol.presentation.features.scenarios.components

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
import androidx.compose.ui.res.stringResource
import tele.read.adamkorol.R

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
            Text(text = stringResource(id = R.string.add_scenario))
        },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { value ->
                        title = value
                    },
                    label = {
                        Text(text = stringResource(id = R.string.title))
                    }
                )
                TextField(
                    value = text,
                    onValueChange = { value ->
                        text = value
                    },
                    label = {
                        Text(text = stringResource(id = R.string.text))
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
                Text(stringResource(id = R.string.save))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}