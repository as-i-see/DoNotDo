package dev.asisee.donotdo.tabs.statistic

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seiko.imageloader.rememberAsyncImagePainter
import dev.asisee.donotdo.Res
import dev.asisee.donotdo.recordRepository
import dev.asisee.donotdo.systemTZ
import dev.asisee.donotdo.toDateString
import dev.asisee.donotdo.toTimeString
import kotlinx.datetime.Instant
import kotlinx.datetime.toLocalDateTime

@Composable
fun Statistic(modifier: Modifier = Modifier, contentModifier: Modifier = Modifier) {
    val painter = rememberAsyncImagePainter(Res.string.statistic_image_url)
    var indexByDay = remember { 0L }
    var previousRecordEpoch = remember { 0L }
    Column(modifier) {
        LazyColumn(contentModifier.fillMaxWidth(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                Image(
                    painter = painter,
                    contentDescription = Res.string.statistic_image_url,
                    modifier = contentModifier.width(200.dp)
                )
            }
            items(recordRepository.getRecords()) {record ->
                Record(21.sp, record.indexByDay, record.toInstant())
            }
        }
    }
}
@Composable
fun Record(fontSize: TextUnit, index: Int, instant: Instant) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(fontSize = fontSize, text = instant.toDateString())
        Text(fontSize = fontSize, text = "$index")
        Text(fontSize = fontSize, text = instant.toTimeString())
    }
}