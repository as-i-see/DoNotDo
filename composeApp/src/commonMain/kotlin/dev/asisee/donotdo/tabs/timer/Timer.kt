package dev.asisee.donotdo.tabs.timer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.russhwolf.settings.Settings
import com.seiko.imageloader.rememberAsyncImagePainter
import dev.asisee.donotdo.Res
import dev.asisee.donotdo.recordRepository
import dev.asisee.donotdo.settings
import dev.asisee.donotdo.systemTZ
import dev.asisee.donotdo.tabs.statistic.data.Record
import dev.asisee.donotdo.toTimeString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.math.sin

const val END_TIME_KEY = "END_TIME"
const val TIMER_MINS_KEY = "MINUTES"
const val MILLIS_IN_MINUTE = 2_000L

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Timer(
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier
) {
    var canSmoke by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    var minutesRemaining by remember { mutableStateOf(0L) }
    var endTimeString by remember { mutableStateOf(Clock.System.now().toTimeString()) }
    val canSmokeImagePainter = rememberAsyncImagePainter(Res.string.can_smoke_image_url)
    val cantSmokeImagePainter = rememberAsyncImagePainter(Res.string.cant_smoke_image_url)
    var timerMinutesText by remember { mutableStateOf(settings.getInt(TIMER_MINS_KEY, 60).toString()) }
    suspend fun startTimer(endTime: Instant) {
        if (Clock.System.now() >= endTime) {
            canSmoke = true
            settings.remove(END_TIME_KEY)
        } else {
            endTimeString = endTime.toTimeString()
            canSmoke = false
            do {
                val now = Clock.System.now()
                minutesRemaining = (endTime - now).inWholeMinutes
                delay(MILLIS_IN_MINUTE)
            } while (now < endTime)
            canSmoke = true
            settings.remove(END_TIME_KEY)
        }
    }
    suspend fun startNewTimer(minutesOfRestraining: Int) {
        val now = Clock.System.now()
        withContext(Dispatchers.IO) {
            recordRepository.addRecord(now)
        }
        val endTime = now.plus(minutesOfRestraining, DateTimeUnit.MINUTE, systemTZ)
        withContext(Dispatchers.IO) {
            settings.putLong(END_TIME_KEY, endTime.epochSeconds)
        }
        startTimer(endTime)
    }
    suspend fun checkTimer() {
        settings.getLongOrNull(END_TIME_KEY)?.let { endTimeEpoch ->
            startTimer(Instant.fromEpochSeconds(endTimeEpoch))
        }
    }

    LaunchedEffect(Unit) { checkTimer() }

    Box(modifier) {
        if (canSmoke) {
            Column(contentModifier.align(Alignment.Center).padding(top = 16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                OutlinedTextField(value = timerMinutesText, onValueChange = { value ->
                    timerMinutesText = if (value.isEmpty() || value.toUIntOrNull() != null) {
                        value
                    } else "60"
                }, label = { Text(Res.string.minutes_input_text) }, singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(200.dp))
                Image(painter = canSmokeImagePainter, contentDescription = Res.string.can_smoke_image_url, modifier = contentModifier.fillMaxWidth().weight(0.9f))
                Button(
                    onClick = { coroutineScope.launch(Dispatchers.Default) {
                        if (timerMinutesText.isEmpty()) timerMinutesText = "60"
                        timerMinutesText.toInt().let { minutesOfRestraining ->
                            startNewTimer(minutesOfRestraining)
                            withContext(Dispatchers.IO) {
                                settings.putInt(TIMER_MINS_KEY, minutesOfRestraining)
                            }
                        }

                    } },
                ) {
                    Text(Res.string.timer_button_text, fontSize = 21.sp)
                }
            }
        } else {
            Column(contentModifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Image(painter = cantSmokeImagePainter, contentDescription = Res.string.cant_smoke_image_url, modifier = contentModifier.fillMaxWidth().weight(0.9f))
                Text(
                    "You can smoke in $minutesRemaining minutes\nat $endTimeString",
                    fontSize = 21.sp,
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}

