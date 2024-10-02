package com.test.teleprompter.domain.repository

import com.test.teleprompter.domain.model.Scenario
import kotlinx.coroutines.flow.Flow

interface ScenarioRepository {
    suspend fun addNewScenario(text: String)
    fun getAllScenariosFlow(): Flow<List<Scenario>>
    suspend fun removeScenario(id: Int)
}