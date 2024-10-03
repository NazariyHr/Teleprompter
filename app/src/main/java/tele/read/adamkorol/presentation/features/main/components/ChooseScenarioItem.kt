package tele.read.adamkorol.presentation.features.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tele.read.adamkorol.domain.model.Scenario
import tele.read.adamkorol.presentation.common.theme.DarkGrey
import tele.read.adamkorol.presentation.common.theme.TeleprompterTheme

/**
 * Created by nazar at 02.10.2024
 */
@Composable
fun ChooseScenarioItem(
    scenario: Scenario,
    onClicked: (scenario: Scenario) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickable {
                onClicked(scenario)
            }
            .fillMaxWidth()
            .background(
                color = DarkGrey,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
    ) {
        Text(text = scenario.title)
    }
}

@Preview
@Composable
private fun ChooseScenarioItemPreview() {
    TeleprompterTheme {
        ChooseScenarioItem(
            Scenario(1, "title", "text"),
            onClicked = {}
        )
    }
}