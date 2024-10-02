package com.test.teleprompter.presentation.features.scenarios.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.teleprompter.R
import com.test.teleprompter.domain.model.Scenario
import com.test.teleprompter.presentation.common.theme.DarkGrey
import com.test.teleprompter.presentation.common.theme.TeleprompterTheme

/**
 * Created by nazar at 01.10.2024
 */
@Composable
fun ScenarioItem(
    scenario: Scenario,
    onRemoveClicked: (scenarioId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = DarkGrey,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
    ) {
        Image(
            modifier = Modifier
                .clickable {
                    onRemoveClicked(scenario.id)
                }
                .size(24.dp)
                .align(Alignment.TopEnd),
            painter = painterResource(id = R.drawable.remove),
            contentDescription = "Remove scenario ${scenario.id}"
        )
        Column {
            Text(
                text = scenario.title,
                fontWeight = FontWeight.Bold
            )
            Text(text = scenario.text)
        }
    }
}

@Preview
@Composable
private fun ScenarioItemPreview() {
    TeleprompterTheme {
        ScenarioItem(
            scenario = Scenario(0, "Some title", "Some text"),
            onRemoveClicked = {}
        )
    }
}