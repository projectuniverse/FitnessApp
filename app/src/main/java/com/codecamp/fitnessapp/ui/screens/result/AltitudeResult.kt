package com.codecamp.fitnessapp.ui.screens.result

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.entry.entryModelOf

@Composable
fun AltitudeResult() {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.8f),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurface,
        )
    ) {
        ProvideChartStyle(
            chartStyle = ChartStyle.fromColors(
                axisGuidelineColor = MaterialTheme.colorScheme.onSurface,
                axisLabelColor = MaterialTheme.colorScheme.onSurface,
                axisLineColor = MaterialTheme.colorScheme.onSurface,
                entityColors = listOf(MaterialTheme.colorScheme.onSurfaceVariant),
                elevationOverlayColor = MaterialTheme.colorScheme.errorContainer
            )
        ) {
            // Sample values
            val chartEntryModel = entryModelOf(4f, 12f, 8f, 16f, 9f)
            /*
             * To map height data to user-set x-axis datapoints,
             * see: https://patrykandpatrick.com/vico/wiki/axes/
             */
            Chart(
                chart = lineChart(),
                model = chartEntryModel,
                modifier = Modifier.padding(10.dp),
                startAxis = startAxis(
                    titleComponent = textComponent(
//                    color = MaterialTheme.colorScheme.onSurface,
                        textSize = 14.sp
                    ),
                    title = "Height (m)"
                ),
                bottomAxis = bottomAxis(
                    titleComponent = textComponent(
//                    color = Color(0xFF4b9ac0),
                        textSize = 14.sp
                    ),
                    title = "Time (min)",
                )
            )
        }
    }
}