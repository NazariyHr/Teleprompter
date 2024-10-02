package com.test.teleprompter.presentation.features.main

sealed class MainScreenAction {
    data class OnAddScenario(val title: String, val text: String) : MainScreenAction()
}