package com.example.pokedexapi

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedexapi.model.PokemonsTypes

@Dao
interface TypesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTypes(types: PokemonsTypes)

    @Query("SELECT * FROM pokemon_types")
    suspend fun getAllTypes(): List<PokemonsTypes>

    @Query("DELETE FROM pokemon_types")
    suspend fun deleteTypes()

    @Query("SELECT * FROM pokemon_types WHERE name = :name")
    suspend fun getTypesByName(name: String?): List<PokemonsTypes>
}