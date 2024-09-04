package dev.asisee.donotdo.database

import dev.asisee.donotdo.tabs.statistic.data.Record
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query

class RealmDatabase {
    private val realm: Realm by lazy {
        val configuration = RealmConfiguration.create(schema = setOf(Record::class))
        Realm.open(configuration)
    }
    fun getAllRecords() : List<Record> = realm.query<Record>().find()

    fun getLastRecord() : Record? = realm.query<Record>().find().lastOrNull()

    fun addRecord(record: Record) = realm.writeBlocking {
        copyToRealm(record)
    }
}