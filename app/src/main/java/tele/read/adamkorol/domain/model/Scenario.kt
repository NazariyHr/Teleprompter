package tele.read.adamkorol.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Scenario(
    val id: Int,
    val title: String,
    val text: String
) : Parcelable
