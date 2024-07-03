package com.example.pokedexapi.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_table")
data class PokemonsModel(
    @PrimaryKey val id: Int,
    val name: String,
    val url: String,
    val img : String
)

