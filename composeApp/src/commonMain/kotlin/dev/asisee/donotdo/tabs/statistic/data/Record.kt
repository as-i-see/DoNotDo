package dev.asisee.donotdo.tabs.statistic.data

import dev.asisee.donotdo.systemTZ
import io.realm.kotlin.types.RealmObject
import kotlinx.datetime.Instant
import kotlinx.datetime.toLocalDateTime

class Record : RealmObject {
    var indexByDay : Int = 0
    var epochSeconds : Long = 0L

    fun toInstant() = Instant.fromEpochSeconds(epochSeconds)
    fun hour() = toInstant().toLocalDateTime(systemTZ).hour
}
