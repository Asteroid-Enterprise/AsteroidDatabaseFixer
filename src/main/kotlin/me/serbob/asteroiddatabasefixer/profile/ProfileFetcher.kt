package me.serbob.asteroiddatabasefixer.profile

import com.google.gson.JsonParser
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URLEncoder
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

object ProfileFetcher {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val semaphore = Semaphore(30) // made, because I kept getting rate-limited

    suspend fun fetchProfile(
        profile: SpoofProfile
    ): Pair<String?, String?> = semaphore.withPermit {
        withContext(Dispatchers.IO) {
            tryPlayerDB(profile.name)
                ?: tryAshcon(profile.name)
                ?: tryMineTools(profile.name)
                ?: (null to null)
        }
    }

    private fun tryPlayerDB(
        name: String
    ): Pair<String?, String?>? {
        return try {
            val encodedName = URLEncoder.encode(name, "UTF-8")
            val request = Request.Builder()
                .url("https://playerdb.co/api/player/minecraft/$encodedName")
                .header("User-Agent", "AsteroidDatabaseFixer/1.0")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful)
                    return null

                val json = JsonParser.parseString(response.body?.string()).asJsonObject
                if (!json["success"].asBoolean)
                    return null

                val player = json["data"].asJsonObject["player"].asJsonObject
                val properties = player["properties"]?.asJsonArray?.get(0)?.asJsonObject ?: return null

                val value = properties["value"]?.asString ?: return null
                val signature = properties["signature"]?.asString ?: return null

                value to signature
            }
        } catch (_: Exception) {
            null
        }
    }

    private fun tryAshcon(
        name: String
    ): Pair<String?, String?>? {
        return try {
            val encodedName = URLEncoder.encode(name, "UTF-8")
            val request = Request.Builder()
                .url("https://api.ashcon.app/mojang/v2/user/$encodedName")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return null

                val json = JsonParser.parseString(response.body?.string()).asJsonObject
                val raw = json["textures"]?.asJsonObject?.get("raw")?.asJsonObject ?: return null

                val value = raw["value"]?.asString ?: return null
                val signature = raw["signature"]?.asString ?: return null

                value to signature
            }
        } catch (_: Exception) {
            null
        }
    }

    private fun tryMineTools(
        name: String
    ): Pair<String?, String?>? {
        return try {
            val uuidRequest = Request.Builder()
                .url("https://api.minetools.eu/uuid/$name")
                .build()

            val uuid = client.newCall(uuidRequest).execute().use { response ->
                if (!response.isSuccessful) return null
                val json = JsonParser.parseString(response.body?.string()).asJsonObject
                json["id"]?.asString ?: return null
            }

            val profileRequest = Request.Builder()
                .url("https://api.minetools.eu/profile/$uuid")
                .build()

            client.newCall(profileRequest).execute().use { response ->
                if (!response.isSuccessful) return null

                val json = JsonParser.parseString(response.body?.string()).asJsonObject
                val properties = json["raw"]?.asJsonObject
                    ?.get("properties")?.asJsonArray
                    ?.get(0)?.asJsonObject ?: return null

                val value = properties["value"]?.asString ?: return null
                val signature = properties["signature"]?.asString ?: return null

                value to signature
            }
        } catch (_: Exception) {
            null
        }
    }

    suspend fun fetchProfiles(
        profiles: List<SpoofProfile>
    ): List<SpoofProfile> = coroutineScope {
        val total = profiles.size
        val checked = AtomicInteger(0)
        val failed = AtomicInteger(0)

        profiles.map { profile ->
            async {
                val (value, signature) = fetchProfile(profile)

                val current = checked.incrementAndGet()
                print("\rChecking profiles: $current / $total (failed: ${failed.get()})")

                if (value == null || signature == null) {
                    failed.incrementAndGet()
                    return@async null
                }

                return@async SpoofProfile(profile.name, profile.uuid, value, signature)
            }
        }.awaitAll().filterNotNull().also {
            println()
        }
    }
}