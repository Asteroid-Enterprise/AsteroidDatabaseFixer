package me.serbob.asteroiddatabasefixer.database.impl.mysql

import me.serbob.asteroiddatabasefixer.database.impl.AsteroidDatabase

abstract class MySQL(
    protected val host: String,
    protected val database: String,
    protected val username: String,
    protected val password: String,
    protected val port: Int,
    protected val ssl: Boolean,
) : AsteroidDatabase() {

    override fun connect() {
        TODO("Not yet implemented")
    }

    override fun disconnect() {
        TODO("Not yet implemented")
    }
}