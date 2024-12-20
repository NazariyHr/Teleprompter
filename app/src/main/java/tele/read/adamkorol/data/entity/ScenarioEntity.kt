package tele.read.adamkorol.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import tele.read.adamkorol.domain.model.Scenario

@Entity
data class ScenarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val text: String
)

fun ScenarioEntity.toScenario(): Scenario {
    return Scenario(
        id,
        title,
        text
    )
}

fun Scenario.toScenarioEntity(): ScenarioEntity {
    return ScenarioEntity(
        id,
        title,
        text
    )
}