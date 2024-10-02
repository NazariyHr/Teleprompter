package com.test.teleprompter.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.test.teleprompter.data.entity.ScenarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScenarioDao {
    @Insert
    suspend fun insert(rate: ScenarioEntity)

    @Query("select * from scenarioentity")
    suspend fun getAll(): List<ScenarioEntity>

    @Query("select * from scenarioentity")
    fun getAllFlow(): Flow<List<ScenarioEntity>>

    @Query("delete from scenarioentity where id = :scenarioId")
    suspend fun delete(scenarioId: Int)
}