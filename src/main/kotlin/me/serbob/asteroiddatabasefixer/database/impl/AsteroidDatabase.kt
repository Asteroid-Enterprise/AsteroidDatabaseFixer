package me.serbob.asteroiddatabasefixer.database.impl

import me.serbob.asteroiddatabasefixer.profile.SpoofProfile
import java.util.UUID

abstract class AsteroidDatabase {

    abstract fun connect()
    abstract fun disconnect()

    abstract fun getProfiles(): List<SpoofProfile> // no need for coroutine as it's just one db call
    abstract suspend fun updateSkin(uuid: UUID, profile: SpoofProfile)
    abstract suspend fun removeProfile(profile: SpoofProfile)
}