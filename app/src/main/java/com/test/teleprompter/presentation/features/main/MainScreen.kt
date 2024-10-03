package com.test.teleprompter.presentation.features.main

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.test.teleprompter.R
import com.test.teleprompter.presentation.common.theme.MainBgColor
import com.test.teleprompter.presentation.common.theme.TeleprompterTheme
import com.test.teleprompter.presentation.features.main.components.AlertDialogChooseScenario
import com.test.teleprompter.presentation.features.scenarios.components.AlertDialogEnterScenario
import com.test.teleprompter.presentation.navigation.Screen

@Composable
fun MainScreenRoot(
    navController: NavController,
    viewModel: MainViewModel =
        hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    MainScreen(
        state = state,
        navigate = {
            navController.navigate(it)
        },
        onAction = viewModel::onAction
    )
}

@Composable
private fun MainScreen(
    state: MainScreenState,
    navigate: (Screen) -> Unit,
    onAction: (MainScreenAction) -> Unit
) {
    val context = LocalContext.current

    var showChooseScenarioDialog by rememberSaveable { mutableStateOf(false) }
    var showEnterScenarioDialog by rememberSaveable { mutableStateOf(false) }

    var permissionsGranted by rememberSaveable {
        mutableStateOf(false)
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            permissionsGranted = permissions.values.all { it }
            if (permissionsGranted) {
                showChooseScenarioDialog = true
            }
        }
    )

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MainBgColor)
                .padding(paddingValues)
                .padding(horizontal = 12.dp)
        ) {
            Box(
                modifier = Modifier.align(Alignment.Center)
            ) {
                Column {
                    Button(
                        onClick = {
                            val cameraPermissionGranted = ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CAMERA
                            ) == PackageManager.PERMISSION_GRANTED
                            val recordAudioPermissionGranted = ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.RECORD_AUDIO
                            ) == PackageManager.PERMISSION_GRANTED
                            val writeStoragePermissionGranted = ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ) == PackageManager.PERMISSION_GRANTED
                            permissionsGranted =
                                cameraPermissionGranted && recordAudioPermissionGranted && writeStoragePermissionGranted
                            if (cameraPermissionGranted) {
                                showChooseScenarioDialog = true
                            } else {
                                requestPermissionLauncher.launch(
                                    arrayOf(
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.RECORD_AUDIO,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                    )
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = stringResource(id = R.string.new_record))
                    }
                    Button(
                        onClick = {
                            navigate(Screen.Scenarios)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = stringResource(id = R.string.my_scenarios))
                    }
                }
            }
        }

        if (showChooseScenarioDialog) {
            AlertDialogChooseScenario(
                onDismissRequest = { showChooseScenarioDialog = false },
                scenarios = state.scenarios,
                onChoose = { scenario ->
                    navigate(Screen.RecordVideo(scenario.id))
                    showChooseScenarioDialog = false
                },
                onAddScenario = {
                    showEnterScenarioDialog = true
                }
            )
        }
        if (showEnterScenarioDialog) {
            AlertDialogEnterScenario(
                onDismissRequest = { showEnterScenarioDialog = false },
                onAdd = { title, text ->
                    onAction(
                        MainScreenAction.OnAddScenario(
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

@Preview
@Composable
private fun MainScreenPreview() {
    TeleprompterTheme {
        MainScreen(
            state = MainScreenState(),
            onAction = {},
            navigate = {}
        )
    }
}