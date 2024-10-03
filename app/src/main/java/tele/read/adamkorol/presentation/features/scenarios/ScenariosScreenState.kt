package tele.read.adamkorol.presentation.features.scenarios

import android.os.Parcelable
import tele.read.adamkorol.domain.model.Scenario
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScenariosScreenState(
    val scenarios: List<Scenario> = emptyList()
) : Parcelable