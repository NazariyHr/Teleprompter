package com.test.teleprompter.presentation.features.scenarios

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.teleprompter.domain.repository.ScenarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScenariosViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val scenarioRepository: ScenarioRepository
) : ViewModel() {
    companion object {
        const val STATE_KEY = "state"
    }

    private var stateValue: ScenariosScreenState
        set(value) {
            savedStateHandle[STATE_KEY] = value
        }
        get() {
            return savedStateHandle.get<ScenariosScreenState>(STATE_KEY)!!
        }
    val state = savedStateHandle.getStateFlow(STATE_KEY, ScenariosScreenState())

    init {
        scenarioRepository
            .getAllScenariosFlow()
            .onEach { scenarios ->
                stateValue = stateValue.copy(
                    scenarios = scenarios
                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: ScenariosScreenAction) {
        when (action) {
            is ScenariosScreenAction.OnAddScenario -> {
                viewModelScope.launch {
                    scenarioRepository.addNewScenario(
                        title = action.title,
                        text = action.text
                    )
                }
            }

            is ScenariosScreenAction.OnRemoveScenario -> {
                viewModelScope.launch {
                    scenarioRepository.removeScenario(action.scenarioId)
                }
            }
        }
    }
}