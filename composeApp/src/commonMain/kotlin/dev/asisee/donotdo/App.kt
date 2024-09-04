package dev.asisee.donotdo

import androidx.compose.material.BottomNavigation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.russhwolf.settings.Settings
import com.seiko.imageloader.ImageLoader
import dev.asisee.donotdo.database.RealmDatabase
import dev.asisee.donotdo.database.RecordRepository
import dev.asisee.donotdo.tabs.TabNavigationItem
import dev.asisee.donotdo.tabs.statistic.StatisticTab
import dev.asisee.donotdo.tabs.timer.TimerTab
import dev.asisee.donotdo.theme.AppTheme
import dev.asisee.donotdo.theme.md_theme_light_primary
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

val settings: Settings = Settings()
val systemTZ = TimeZone.currentSystemDefault()
val recordRepository = RecordRepository()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun App() = AppTheme {
    TabNavigator(TimerTab.instance) {
        Scaffold(
            content = { CurrentTab() },
            bottomBar = { BottomNavigation(backgroundColor = md_theme_light_primary) {
                TabNavigationItem(TimerTab.instance)
                TabNavigationItem(StatisticTab.instance)
            }}
        )
    }
}

fun Int.format() = if (this < 10) "0$this" else this

fun Instant.toTimeString() = toLocalDateTime(systemTZ).time.let { time ->
    "${time.hour.format()}:${time.minute.format()}"
}

fun Instant.toDateString() = toLocalDateTime(systemTZ).date.let { date ->
    "${date.dayOfMonth.format()} ${date.month.name}"
}

fun Instant.toDateTimeString() = "${toDateString()}\t${toTimeString()}"

fun Instant.day() = toLocalDateTime(systemTZ).dayOfYear

fun Instant.year() = toLocalDateTime(systemTZ).year