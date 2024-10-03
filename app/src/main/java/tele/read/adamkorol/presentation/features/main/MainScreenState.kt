package tele.read.adamkorol.presentation.features.main

import android.os.Parcelable
import tele.read.adamkorol.domain.model.Scenario
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainScreenState(
    val scenarios: List<Scenario> = emptyList()
) : Parcelable