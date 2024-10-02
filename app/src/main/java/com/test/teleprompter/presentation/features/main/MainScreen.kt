package com.test.teleprompter.presentation.features.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.test.teleprompter.presentation.common.theme.MainBgColor
import com.test.teleprompter.presentation.common.theme.TeleprompterTheme
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
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MainBgColor)
                .padding(paddingValues)
                .padding(horizontal = 12.dp)
        ) {
            Button(
                onClick = {
                    //todo show popup to choose scenario
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Новая запись")
            }
            Button(
                onClick = {
                    navigate(Screen.Scenarios)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Мои сценарии")
            }
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