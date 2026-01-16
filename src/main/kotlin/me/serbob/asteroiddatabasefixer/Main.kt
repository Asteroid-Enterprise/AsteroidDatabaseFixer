package me.serbob.asteroiddatabasefixer

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import me.serbob.asteroiddatabasefixer.command.CommandLineParser
import me.serbob.asteroiddatabasefixer.database.Database
import me.serbob.asteroiddatabasefixer.database.DatabaseManager
import me.serbob.asteroiddatabasefixer.profile.ProfileFetcher
import me.serbob.asteroiddatabasefixer.profile.SpoofProfile

private lateinit var parser: CommandLineParser

fun main(
    args: Array<String>
) {
    parser = CommandLineParser(args)

    val (database: Database?, testing: String?) = parser.parse()

    if (database == null)
        throw NotImplementedError("The database type you are trying to use isn't yet supported.")

    DatabaseManager.initialize(database)

    runBlocking {
        val dbProfiles: List<SpoofProfile> = DatabaseManager.database.getProfiles()
        val validProfiles: List<SpoofProfile> = ProfileFetcher.fetchProfiles(dbProfiles)

        val validNames = validProfiles.map { it.name }.toSet()
        val invalidProfiles = dbProfiles.filter { it.name !in validNames }

        println("----------------------")
        println("Total: ${dbProfiles.size}")
        println("Valid: ${validProfiles.size}")
        println("Invalid: ${invalidProfiles.size}")
        println("----------------------")

        invalidProfiles.forEach { profile ->
//        println("Profile: ${profile.name}")
            DatabaseManager.database.removeProfile(profile)
        }

        println("----------------------")
        println("Successfully removed ${invalidProfiles.size} from the database")
        println("----------------------")
    }
}