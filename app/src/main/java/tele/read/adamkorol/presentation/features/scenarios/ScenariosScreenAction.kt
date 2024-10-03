package tele.read.adamkorol.presentation.features.scenarios

sealed class ScenariosScreenAction {
    data class OnAddScenario(val title: String, val text: String) : ScenariosScreenAction()
    data class OnRemoveScenario(val scenarioId: Int) : ScenariosScreenAction()
}