package me.serbob.asteroiddatabasefixer.profile

import java.util.UUID

data class SpoofProfile(
    val name: String,
    val uuid: UUID,
    val skinValue: String,
    val skinSignature: String,
)
