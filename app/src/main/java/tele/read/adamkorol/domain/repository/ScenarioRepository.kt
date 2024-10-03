package tele.read.adamkorol.domain.repository

import tele.read.adamkorol.domain.model.Scenario
import kotlinx.coroutines.flow.Flow

interface ScenarioRepository {
    suspend fun addNewScenario(title: String, text: String)
    fun getAllScenariosFlow(): Flow<List<Scenario>>
    suspend fun removeScenario(id: Int)
    suspend fun getScenario(id: Int): Scenario
}