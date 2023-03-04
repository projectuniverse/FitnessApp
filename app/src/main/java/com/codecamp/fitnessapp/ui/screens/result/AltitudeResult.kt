package com.codecamp.fitnessapp.ui.screens.result

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.axis.horizontal.HorizontalAxis
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.entry.*
import java.math.RoundingMode
import kotlin.math.ceil

/*
 * For this chart to work correctly, Track objects must
 * be created every 10 seconds during an outside workout.
 */
@Composable
fun AltitudeResult(
    //values: List<Pair<Int, Float>>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(1f),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
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
            class Entry(
                val time: Int,
                override val x: Float,
                override val y: Float,
            ) : ChartEntry {
                override fun withY(y: Float) = Entry(time, x, y)
            }

            /*
             * Sample data set. Replace with the data set given in constructor.
             */
            var values: MutableList<Pair<Int, Float>> = mutableListOf()
            var start = 1677633370
            for (i in 0 until 720) {
                values.add(start + i*10 to (0..20).random().toFloat())
            }

            var startTime = 0
            val chartEntryModel =
                values
                .mapIndexed { index, (time, height) ->
                    if (index == 0) {
                        startTime = time
                        Entry(0, index.toFloat(), height)
                    }
                    else {
                        Entry(time - startTime, index.toFloat(), height)
                    }
                }
                .let { ChartEntryModelProducer(it) }

            val axisValueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, chartValues ->
                (chartValues.chartEntryModel.entries.first().getOrNull(value.toInt()) as? Entry)
                    ?.run {
                        if (time == 0) {
                            "0"
                        } else {
                            "${(time / 60f).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()}"
                        }
                    }
                    .orEmpty()
            }

            /*
             * Denotes at what interval x-axis values should be displayed.
             * E.g., chartSpacing = 18 means every third minute will be displayed
             * on the x-axis (so 0, 3, 6, 9, ...) - because Track objects are created
             * every 10 sec so there are 18 track Objects created in 3 minutes.
             * Although only every third minute is displayed on the x-axis,
             * ALL height-values are in the graph (not just every 18th).
             */
            val chartSpacing =
                if (values.size < 7) {
                    1
                }
                else {
                    6 * ceil((values.size / 6) / 10f).toInt()
                }

            Chart(
                chart = lineChart(
                    lines = listOf(
                        LineChart.LineSpec(
                            pointSizeDp = 1f,
                            //lineColor = 4284612846
                        )
                    ),
                    spacing = 3.dp
                ),
                model = chartEntryModel.getModel(),
                startAxis = startAxis(
                    titleComponent = textComponent(
                        textSize = 14.sp
                    ),
                    title = "Height (m)",
                ),
                bottomAxis = bottomAxis(
                    titleComponent = textComponent(
                        textSize = 14.sp,
                    ),
                    title = "Time (min)",
                    valueFormatter = axisValueFormatter,
                    tickPosition = HorizontalAxis.TickPosition.Center(
                        0,
                        chartSpacing
                    )
                )
            )
        }
    }
}