package com.test.teleprompter.presentation.features.video_record

import android.os.Parcelable
import com.test.teleprompter.domain.model.Scenario
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoRecordScreenState(
    val scenario: Scenario? = null
) : Parcelable