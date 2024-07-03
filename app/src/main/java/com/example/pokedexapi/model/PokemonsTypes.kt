package com.example.pokedexapi.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_types")
data class PokemonsTypes(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val name: String,
    val type: String
)
