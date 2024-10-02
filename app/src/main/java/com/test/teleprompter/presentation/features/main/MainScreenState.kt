package com.test.teleprompter.presentation.features.main

import android.os.Parcelable
import com.test.teleprompter.domain.model.Scenario
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainScreenState(
    val scenarios: List<Scenario> = emptyList()
) : Parcelable