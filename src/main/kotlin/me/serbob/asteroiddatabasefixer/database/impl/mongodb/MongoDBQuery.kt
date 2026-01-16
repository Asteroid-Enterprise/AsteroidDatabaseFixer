package me.serbob.asteroiddatabasefixer.database.impl.mongodb

import me.serbob.asteroiddatabasefixer.profile.SpoofProfile
import java.util.UUID

class MongoDBQuery(
    uri: String
) : MongoDB(
    uri
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