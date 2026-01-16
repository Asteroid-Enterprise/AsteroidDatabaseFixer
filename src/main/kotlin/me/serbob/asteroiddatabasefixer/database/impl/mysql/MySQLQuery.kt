package me.serbob.asteroiddatabasefixer.database.impl.mysql

import me.serbob.asteroiddatabasefixer.profile.SpoofProfile
import java.util.UUID

class MySQLQuery(
    host: String,
    database: String,
    username: String,
    password: String,
    port: Int,
    ssl: Boolean
) : MySQL(
    host,
    database,
    username,
    password,
    port,
    ssl
) {

    override fun getProfiles(): List<SpoofProfile> {
        TODO("Not yet implemented")
    }

    override suspend fun updateSkin(
        uuid: UUID,
        profile: SpoofProfile
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun removeProfile(
        profile: SpoofProfile
    ) {
        TODO("Not yet implemented")
    }
}