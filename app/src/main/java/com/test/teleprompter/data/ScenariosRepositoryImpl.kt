package com.test.teleprompter.data

import com.test.teleprompter.data.entity.ScenarioEntity
import com.test.teleprompter.data.entity.toScenario
import com.test.teleprompter.domain.model.Scenario
import com.test.teleprompter.domain.repository.ScenarioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class ScenariosRepositoryImpl(
    private val scenarioDatabase: ScenarioDatabase
) : ScenarioRepository {

    override suspend fun addNewScenario(title: String, text: String) {
        scenarioDatabase.scenarioDao.insert(ScenarioEntity(0, title, text))
    }

    override fun getAllScenariosFlow(): Flow<List<Scenario>> =
        scenarioDatabase.scenarioDao.getAllFlow().distinctUntilChanged()
            .map { it.map { it.toScenario() } }

    override suspend fun removeScenario(id: Int) {
        scenarioDatabase.scenarioDao.delete(id)
    }
}