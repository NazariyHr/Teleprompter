package com.test.teleprompter.presentation.features.scenarios

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScenariosScreenState(
    val title: String = ""
) : Parcelable