package com.test.teleprompter.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.teleprompter.data.dao.ScenarioDao
import com.test.teleprompter.data.entity.ScenarioEntity

@Database(
    entities = [ScenarioEntity::class],
    version = 1
)
abstract class ScenarioDatabase : RoomDatabase() {
    abstract val scenarioDao: ScenarioDao
}