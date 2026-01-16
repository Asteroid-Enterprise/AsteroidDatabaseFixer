package me.serbob.asteroiddatabasefixer.database.impl.sqlite

import me.serbob.asteroiddatabasefixer.database.impl.AsteroidDatabase
import java.sql.Connection
import java.sql.DriverManager

abstract class SQLite(
    protected val database: String
) : AsteroidDatabase() {

    protected lateinit var connection: Connection

    private fun createConnection(): Connection {
        return DriverManager.getConnection("jdbc:sqlite:$database")
    }

    override fun connect() {
        connection = createConnection()
    }

    override fun disconnect() {
        connection.close()
    }
}