package dev.asisee.donotdo.database

import dev.asisee.donotdo.day
import dev.asisee.donotdo.tabs.statistic.data.Record
import dev.asisee.donotdo.year
import kotlinx.datetime.Instant

class RecordRepository {
    private val database = RealmDatabase()

    fun getRecords() = database.getAllRecords()

    fun addRecord(instant: Instant) {
        val prevRecord = database.getLastRecord()
        if (prevRecord == null || (instant.day() != prevRecord.toInstant().day() && instant.year() == prevRecord.toInstant().year())) {
            database.addRecord(Record().apply {
                epochSeconds = instant.epochSeconds
                indexByDay = 1
            })
        } else {
            database.addRecord(Record().apply {
                epochSeconds = instant.epochSeconds
                indexByDay = prevRecord.indexByDay + 1
            })
        }
    }
}