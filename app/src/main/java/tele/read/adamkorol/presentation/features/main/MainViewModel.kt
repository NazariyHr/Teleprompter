package tele.read.adamkorol.presentation.features.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import tele.read.adamkorol.domain.repository.ScenarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val scenarioRepository: ScenarioRepository
) : ViewModel() {
    companion object {
        const val STATE_KEY = "state"
    }

    private var stateValue: MainScreenState
        set(value) {
            savedStateHandle[STATE_KEY] = value
        }
        get() {
            return savedStateHandle.get<MainScreenState>(STATE_KEY)!!
        }
    val state = savedStateHandle.getStateFlow(STATE_KEY, MainScreenState())

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

    fun onAction(action: MainScreenAction) {
        when (action) {
            is MainScreenAction.OnAddScenario -> {
                viewModelScope.launch {
                    scenarioRepository.addNewScenario(
                        title = action.title,
                        text = action.text
                    )
                }
            }
        }
    }
}