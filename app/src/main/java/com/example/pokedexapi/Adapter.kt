package com.example.pokedexapi

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapi.model.PokemonsModel
import com.example.pokedexapi.model.PokemonsTypes
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import com.bumptech.glide.Glide


class Adapter(val context: Context,private val pokemonList: List<PokemonsModel>) : RecyclerView.Adapter<Adapter.PokeViewHolder>(){

    inner class PokeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameElem: TextView = view.findViewById(R.id.item_name)
        val idElem: TextView = view.findViewById(R.id.item_id)
        val imgElem: ImageView = view.findViewById(R.id.item_img)
        val imgPokeElem : ImageView = view.findViewById(R.id.item_pokeball)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val selectedPokemon = pokemonList[position]
                    val intent = Intent(context, DetailActivity::class.java).apply {
                        putExtra("id", selectedPokemon.id)
                        putExtra("name", selectedPokemon.name)
                        putExtra("img", selectedPokemon.img)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokeViewHolder(layoutInflater.inflate(R.layout.item_list, parent, false))
    }

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.nameElem.text = pokemon.name
        holder.idElem.text = pokemon.id.toString()
        Glide.with(context).load(pokemon.img).into(holder.imgElem)
        Glide.with(context).load("https://upload.wikimedia.org/wikipedia/commons/thumb/5/51/Pokebola-pokeball-png-0.png/601px-Pokebola-pokeball-png-0.png").into(holder.imgPokeElem)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

}