package tele.read.adamkorol.presentation.features.video_record

import android.os.Parcelable
import tele.read.adamkorol.domain.model.Scenario
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoRecordScreenState(
    val scenario: Scenario? = null
) : Parcelable