package com.test.teleprompter.presentation.features.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.test.teleprompter.R
import com.test.teleprompter.domain.model.Scenario

@Composable
fun AlertDialogChooseScenario(
    onDismissRequest: () -> Unit,
    scenarios: List<Scenario>,
    onChoose: (Scenario) -> Unit,
    onAddScenario: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.choose_scenario))
        },
        text = {
            if (scenarios.isEmpty()) {
                Button(
                    onClick = {
                        onAddScenario()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(id = R.string.add_scenario))
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(scenarios) { scenario ->
                        ChooseScenarioItem(scenario = scenario, onClicked = onChoose)
                    }
                }
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
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