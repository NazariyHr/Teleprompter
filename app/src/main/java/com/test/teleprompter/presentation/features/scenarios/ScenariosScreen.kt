package com.test.teleprompter.presentation.features.scenarios

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.test.teleprompter.R
import com.test.teleprompter.presentation.common.theme.MainBgColor
import com.test.teleprompter.presentation.common.theme.TeleprompterTheme
import com.test.teleprompter.presentation.features.scenarios.components.AlertDialogEnterScenario
import com.test.teleprompter.presentation.features.scenarios.components.ScenarioItem

@Composable
fun ScenariosScreenRoot(
    navController: NavController,
    viewModel: ScenariosViewModel =
        hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    MainScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun MainScreen(
    state: ScenariosScreenState,
    onAction: (ScenariosScreenAction) -> Unit
) {
    var showEnterScenarioDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MainBgColor)
                .padding(paddingValues)
                .padding(12.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = stringResource(id = R.string.scenarios)
                        )
                    }
                }
                items(state.scenarios) { scenario ->
                    ScenarioItem(
                        scenario = scenario,
                        onRemoveClicked = {
                            onAction(ScenariosScreenAction.OnRemoveScenario(scenarioId = it))
                        }
                    )
                }
            }

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 12.dp),
                onClick = {
                    showEnterScenarioDialog = true
                },
                shape = FloatingActionButtonDefaults.largeShape
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }

            if (showEnterScenarioDialog) {
                AlertDialogEnterScenario(
                    onDismissRequest = { showEnterScenarioDialog = false },
                    onAdd = { title, text ->
                        onAction(
                            ScenariosScreenAction.OnAddScenario(
                                title = title,
                                text = text
                            )
                        )
                        showEnterScenarioDialog = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    TeleprompterTheme {
        MainScreen(
            state = ScenariosScreenState(),
            onAction = {}
        )
    }
}