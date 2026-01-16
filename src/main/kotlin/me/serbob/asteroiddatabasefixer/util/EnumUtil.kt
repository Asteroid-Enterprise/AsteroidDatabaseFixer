package me.serbob.asteroiddatabasefixer.util

inline fun <reified T : Enum<T>> enumValueOfOrNull(
    name: String
): T? {
    return try {
        enumValueOf<T>(name)
    } catch (_: IllegalArgumentException) {
        null
    }
}