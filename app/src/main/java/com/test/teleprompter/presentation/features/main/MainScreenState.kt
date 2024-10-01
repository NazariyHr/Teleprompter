package com.test.teleprompter.presentation.features.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainScreenState(
    val title: String = ""
) : Parcelable