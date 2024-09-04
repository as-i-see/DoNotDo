package dev.asisee.donotdo.tabs.statistic

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FeatherIcons
import compose.icons.feathericons.Info
import dev.asisee.donotdo.Res

internal class StatisticTab : Tab {
    @Composable
    override fun Content() {
        Statistic(modifier = Modifier.fillMaxSize().padding(top = 16.dp, bottom = 100.dp), Modifier.padding(start = 16.dp, end = 16.dp))
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = Res.string.statistic_tab
            val icon = rememberVectorPainter(FeatherIcons.Info)
            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    companion object {
        val instance = StatisticTab()
    }
}