package me.serbob.asteroiddatabasefixer.database

import me.serbob.asteroiddatabasefixer.database.impl.AsteroidDatabase
import me.serbob.asteroiddatabasefixer.database.impl.sqlite.SQLiteQuery

object DatabaseManager {

    lateinit var database: AsteroidDatabase

    fun initialize(
        databaseType: Database,
    ) {
        database = when (databaseType) {
            Database.MONGODB -> TODO()
            Database.MYSQL -> TODO()
            Database.SQLITE -> SQLiteQuery("asteroid.db")
            else -> TODO()
        }

        database.connect()
    }
}