package com.test.teleprompter.presentation.features.video_record

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoRecordScreenState(
    val title: String = "VideoRecord"
) : Parcelable