package com.test.teleprompter.presentation.features.video_record

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.teleprompter.domain.repository.ScenarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoRecordViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val scenarioRepository: ScenarioRepository
) : ViewModel() {
    companion object {
        const val STATE_KEY = "state"
    }

    private var stateValue: VideoRecordScreenState
        set(value) {
            savedStateHandle[STATE_KEY] = value
        }
        get() {
            return savedStateHandle.get<VideoRecordScreenState>(STATE_KEY)!!
        }
    val state = savedStateHandle.getStateFlow(STATE_KEY, VideoRecordScreenState())

    fun saveScenario(scenarioId: Int) {
        viewModelScope.launch {
            val s = scenarioRepository.getScenario(scenarioId)
            stateValue = stateValue.copy(scenario = s)
        }
    }
}