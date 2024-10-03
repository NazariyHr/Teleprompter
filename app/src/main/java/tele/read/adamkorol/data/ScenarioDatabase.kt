package tele.read.adamkorol.data

import androidx.room.Database
import androidx.room.RoomDatabase
import tele.read.adamkorol.data.dao.ScenarioDao
import tele.read.adamkorol.data.entity.ScenarioEntity

@Database(
    entities = [ScenarioEntity::class],
    version = 1
)
abstract class ScenarioDatabase : RoomDatabase() {
    abstract val scenarioDao: ScenarioDao
}