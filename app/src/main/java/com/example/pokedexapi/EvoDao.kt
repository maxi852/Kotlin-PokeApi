package com.example.pokedexapi

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedexapi.model.PokemonsEvo
import com.example.pokedexapi.model.PokemonsModel
import com.example.pokedexapi.model.PokemonsTypes

@Dao
interface EvoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvo(types: PokemonsEvo)

    @Query("SELECT * FROM pokemon_ev")
    suspend fun getAllEvo(): List<PokemonsEvo>

    @Query("DELETE FROM pokemon_ev")
    suspend fun deleteEvo()

    @Query("SELECT P.* FROM pokemon_table P left join pokemon_ev E on E.name = P.name where E.url = (select url from pokemon_ev WHERE name = :name)")
    suspend fun getEvoChain(name: String?): List<PokemonsModel>

}