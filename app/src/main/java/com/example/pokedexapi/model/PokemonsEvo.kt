package com.example.pokedexapi.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_ev")
data class PokemonsEvo(
    @PrimaryKey val name: String,
    val url: String,
)
