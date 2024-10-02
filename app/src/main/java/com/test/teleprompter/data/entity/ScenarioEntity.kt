package com.test.teleprompter.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.teleprompter.domain.model.Scenario

@Entity
data class ScenarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val text: String
)

fun ScenarioEntity.toScenario(): Scenario {
    return Scenario(
        id,
        text
    )
}

fun Scenario.toScenarioEntity(): ScenarioEntity {
    return ScenarioEntity(
        id,
        text
    )
}