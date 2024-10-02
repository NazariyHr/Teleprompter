package com.test.teleprompter.presentation.features.scenarios

sealed class ScenariosScreenAction {
    data class OnAddScenario(val title: String, val text: String) : ScenariosScreenAction()
    data class OnRemoveScenario(val scenarioId: Int) : ScenariosScreenAction()
}