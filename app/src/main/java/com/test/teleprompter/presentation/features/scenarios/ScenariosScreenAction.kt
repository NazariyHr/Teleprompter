package com.test.teleprompter.presentation.features.scenarios

sealed class ScenariosScreenAction {
    data object OnScenariosListClicked : ScenariosScreenAction()
}