package com.mehedi.pokochar.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mehedi.pokochar.R
import com.mehedi.pokochar.network.Pokemon

class PokemonAdapter(private val context: Context) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {
    
    private var pokemonList = mutableListOf<Pokemon>()
    
    fun updatePokemonList(pokemons: List<Pokemon>) {
        pokemonList.clear()
        pokemonList.addAll(pokemons)
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.pokemon_item, parent, false)
        return PokemonViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.bind(pokemon)
    }
    
    override fun getItemCount(): Int = pokemonList.size
    
    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        
        private val pokemonImage: ImageView = itemView.findViewById(R.id.pokemon_image)
        private val pokemonName: TextView = itemView.findViewById(R.id.pokemon_name)
        
        fun bind(pokemon: Pokemon) {
            Glide.with(itemView.context).load(pokemon.sprites?.frontDefault).into(pokemonImage)
            pokemonName.text = "#${pokemon.id} - ${pokemon.name}"
        }
    }
}