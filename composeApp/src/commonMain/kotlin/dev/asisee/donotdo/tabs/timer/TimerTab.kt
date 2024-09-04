package dev.asisee.donotdo.tabs.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FeatherIcons
import compose.icons.feathericons.Clock
import dev.asisee.donotdo.Res

internal class TimerTab : Tab {
    @Composable
    override fun Content() {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Timer(Modifier.fillMaxSize().padding(bottom = 150.dp), Modifier.padding(start = 16.dp, end = 16.dp))
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = Res.string.timer_tab
            val icon = rememberVectorPainter(FeatherIcons.Clock)
            return remember { TabOptions(index = 0u, title = title, icon = icon) }
        }

    companion object {
        val instance = TimerTab()
    }
}
