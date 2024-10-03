package tele.read.adamkorol.presentation.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

sealed class Screen {
    @Parcelize
    @Serializable
    data object Main : Screen(), Parcelable

    @Parcelize
    @Serializable
    data object Scenarios : Screen(), Parcelable

    @Parcelize
    @Serializable
    data class RecordVideo(val scenarioId: Int) : Screen(), Parcelable
}