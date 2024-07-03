package com.example.pokedexapi

import retrofit2.http.GET
import retrofit2.http.Url
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface PokemonApiService {
    @GET
    suspend fun getPokemons(@Url url: String): PokemonResponse

    @GET
    suspend fun getPokemon(@Url url: String): pokeInfoResponse

    @GET
    suspend fun getEvolutions(@Url url: String): evoResponse
}

data class PokemonResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResult>
)

data class evoResponse(
    val evolution_chain: EvolutionChain,
)

data class EvolutionChain(
    val url: String,
)

data class PokemonResult(
    val name: String,
    val url: String
)

data class pokeInfoResponse(
    val types: List<typesResult>,
    val sprites: Sprites
)

data class Sprites(
    val front_default: String,
)

data class typesResult(
    val type: Type,
)

data class Type(
    val name: String,
)

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }

}