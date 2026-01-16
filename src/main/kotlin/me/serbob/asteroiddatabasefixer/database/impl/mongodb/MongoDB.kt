package me.serbob.asteroiddatabasefixer.database.impl.mongodb

import me.serbob.asteroiddatabasefixer.database.impl.AsteroidDatabase

abstract class MongoDB(
    protected val uri: String
) : AsteroidDatabase() {

    override fun connect() {
        TODO("Not yet implemented")
    }

    override fun disconnect() {
        TODO("Not yet implemented")
    }
}