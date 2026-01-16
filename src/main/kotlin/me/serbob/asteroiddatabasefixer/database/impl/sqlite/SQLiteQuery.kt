package me.serbob.asteroiddatabasefixer.database.impl.sqlite

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.serbob.asteroiddatabasefixer.database.util.TABLE_NAME
import me.serbob.asteroiddatabasefixer.profile.SpoofProfile
import java.util.*

open class SQLiteQuery(
    database: String
) : SQLite(
    database
) {

    override fun getProfiles(): List<SpoofProfile> {
        val profiles = mutableListOf<SpoofProfile>()

        connection.prepareStatement("SELECT * FROM $TABLE_NAME").use { statement ->
            statement.executeQuery().use { resultSet ->
                while (resultSet.next()) {
                    val name: String = resultSet.getString("name")
                    val uuid: UUID = UUID.fromString(resultSet.getString("player_uuid"))
                    val skinValue: String = resultSet.getString("skin_value")
                    val skinSignature: String = resultSet.getString("skin_signature")

                    val profile = SpoofProfile(name, uuid, skinValue, skinSignature)

                    profiles.add(profile)
                }
            }
        }

        return profiles
    }

    override suspend fun updateSkin(
        uuid: UUID,
        profile: SpoofProfile
    )  = withContext(Dispatchers.IO) {
        connection.prepareStatement("""
        UPDATE $TABLE_NAME
        SET skin_value = ?, skin_signature = ?
        WHERE name = ?
        """.trimIndent()).use { statement ->
            statement.setString(1, profile.skinValue)
            statement.setString(2, profile.skinSignature)
            statement.setString(3, profile.name)
            statement.executeUpdate()
        }

        Unit
    }

    override suspend fun removeProfile(
        profile: SpoofProfile
    ) = withContext(Dispatchers.IO) {
        connection.prepareStatement("DELETE FROM $TABLE_NAME WHERE name = ?").use { statement ->
            statement.setString(1, profile.name)
            statement.executeUpdate()
        }

        Unit
    }
}