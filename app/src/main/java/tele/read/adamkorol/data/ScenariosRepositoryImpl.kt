package tele.read.adamkorol.data

import tele.read.adamkorol.data.entity.ScenarioEntity
import tele.read.adamkorol.data.entity.toScenario
import tele.read.adamkorol.domain.model.Scenario
import tele.read.adamkorol.domain.repository.ScenarioRepository
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

    override suspend fun getScenario(id: Int): Scenario {
        return scenarioDatabase.scenarioDao.get(id).toScenario()
    }
}