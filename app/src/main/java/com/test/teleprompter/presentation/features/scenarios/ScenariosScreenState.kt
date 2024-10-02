package com.test.teleprompter.presentation.features.scenarios

import android.os.Parcelable
import com.test.teleprompter.domain.model.Scenario
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScenariosScreenState(
    val scenarios: List<Scenario> = emptyList()
) : Parcelable