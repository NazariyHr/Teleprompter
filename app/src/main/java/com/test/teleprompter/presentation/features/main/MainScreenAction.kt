package com.test.teleprompter.presentation.features.main

sealed class MainScreenAction {
    data object OnScenariosListClicked : MainScreenAction()
}