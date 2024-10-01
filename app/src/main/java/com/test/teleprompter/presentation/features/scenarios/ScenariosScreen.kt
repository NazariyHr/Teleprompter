package com.test.teleprompter.presentation.features.scenarios

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MainBgColor)
                .padding(paddingValues)
                .padding(horizontal = 12.dp)
        ) {

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