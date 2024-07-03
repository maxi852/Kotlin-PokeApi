package com.example.pokedexapi

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapi.model.PokemonsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.pokedexapi.AppDatabase
import com.example.pokedexapi.model.PokemonsEvo
import com.example.pokedexapi.model.PokemonsTypes
import kotlinx.coroutines.async

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private var pokemonList = mutableListOf<String>()

    //Base de datos
    private lateinit var database: AppDatabase
    private lateinit var pokeDao: PokemonsDao
    private lateinit var typesDao: TypesDao
    private lateinit var evoDao: EvoDao

    val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("MainActivity", "onCreate called")


        database = AppDatabase.getDatabase(this)
        pokeDao = database.pokeDao()
        typesDao = database.typesDao()
        evoDao = database.evoDao()


        recyclerView = findViewById(R.id.Rec_Poke)
        recyclerView.layoutManager = LinearLayoutManager(this)



        lifecycleScope.launch {
            try {
                var storedPokemonsAdap = pokeDao.getAllPokemons()

                if (storedPokemonsAdap.isEmpty()) {
                    Log.i("MainActivity", "No records found")
                    fetchAndStorePokemons(this@MainActivity)
                } else {
                    Log.i("MainActivity", "Records found")
                }

                storedPokemonsAdap = pokeDao.getAllPokemons()


                adapter = Adapter(this@MainActivity, storedPokemonsAdap)
                recyclerView.adapter = adapter
            } catch (e:Exception){
                Log.e("MainActivity", "Error in onCreate: ${e.message}")
            }

        }
    }

    /*
    private suspend fun loadPokemons() {
        withContext(Dispatchers.IO) {
            val pokemons = pokeDao.getAllPokemons()
            withContext(Dispatchers.Main) {
                adapter.setPokemons(pokemons)
            }
        }
    }
     */

    suspend fun fetchAndStorePokemons(context: Context) {
        try {
            withContext(Dispatchers.IO) {

                val response =RetrofitInstance.api.getPokemons("https://pokeapi.co/api/v2/pokemon?limit=151&offset=0")

                response.results.mapIndexed { index, pokemonResult ->
                        withContext(Dispatchers.IO) {
                            val responseImg = RetrofitInstance.api.getPokemon(pokemonResult.url)
                            pokeDao.insertAll(PokemonsModel(id= index + 1 ,name = pokemonResult.name, url = pokemonResult.url,img=responseImg.sprites.front_default))
                        }

                        withContext(Dispatchers.IO) {
                            val responseTypes = RetrofitInstance.api.getPokemon(pokemonResult.url)
                            responseTypes.types.map {
                                typesDao.insertTypes(PokemonsTypes(name = pokemonResult.name, type = it.type.name))
                            }
                        }

                        withContext(Dispatchers.IO) {
                            val responseEvo = RetrofitInstance.api.getEvolutions("https://pokeapi.co/api/v2/pokemon-species/${pokemonResult.name}")
                            evoDao.insertEvo(PokemonsEvo(name=pokemonResult.name,url=responseEvo.evolution_chain.url))
                        }

                }

                val storedPokemons = pokeDao.getAllPokemons()
                /*
                for (pokemon in storedPokemons) {
                    Log.i("MainActivity","${pokemon.img},${pokemon.name}")
                    withContext(Dispatchers.IO) {
                        val responseTypes = RetrofitInstance.api.getPokemon(pokemon.url)
                        responseTypes.types.map {
                            typesDao.insertTypes(PokemonsTypes(name = pokemon.name, type = it.type.name))
                        }
                    }

                    withContext(Dispatchers.IO) {
                        val responseEvo = RetrofitInstance.api.getEvolutions("https://pokeapi.co/api/v2/pokemon-species/${pokemon.name}")
                        evoDao.insertEvo(PokemonsEvo(name=pokemon.name,url=responseEvo.evolution_chain.url))
                    }

                }

                 */
            }
        }catch (e: Exception) {
                Log.i("MainActivity", "Error fetching or storing pokemons: ${e.message}")
            }

    }



    private suspend fun deleteAllPokemon() {
        withContext(Dispatchers.IO) {
            pokeDao.deleteAll()
            typesDao.deleteTypes()
            evoDao.deleteEvo()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}