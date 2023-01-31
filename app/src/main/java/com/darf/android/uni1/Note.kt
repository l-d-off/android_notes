package com.darf.android.uni1

import androidx.room.Entity
import java.io.Serializable
import java.time.LocalDate

@Entity(primaryKeys = ["id", "note_id"])
data class Note(
    val id: String,
    val note_id: Long,
    val note_text: String?
) : Serializable