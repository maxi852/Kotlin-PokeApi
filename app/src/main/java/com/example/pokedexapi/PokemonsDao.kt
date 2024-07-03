package com.example.pokedexapi

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedexapi.model.PokemonsModel
import com.example.pokedexapi.model.PokemonsTypes

@Dao
interface PokemonsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemons: PokemonsModel)

    @Query("SELECT * FROM pokemon_table")
    suspend fun getAllPokemons(): List<PokemonsModel>

    @Query("DELETE FROM pokemon_table")
    suspend fun deleteAll()
}

