package me.serbob.asteroiddatabasefixer.command

import me.serbob.asteroiddatabasefixer.database.Database
import me.serbob.asteroiddatabasefixer.util.enumValueOfOrNull
import java.util.Locale

/*
 * args[0] = databaseType (ALWAYS)
 *
 */
class CommandLineParser(
    args: Array<String>
) {

    private val databaseType: Database? = args.getOrNull(0)?.let { type ->
        enumValueOfOrNull<Database>(type.uppercase(Locale.ENGLISH))
    }

    fun parse(): Pair<Database?, String?> {
        if (databaseType == null)
            return Pair(null, null)

        return Pair(databaseType, null)
    }
}