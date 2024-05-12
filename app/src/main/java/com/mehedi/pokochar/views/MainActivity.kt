package com.mehedi.pokochar.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mehedi.pokochar.network.ApiClient
import com.mehedi.pokochar.network.DataState
import com.mehedi.pokochar.network.Pokemon
import com.mehedi.pokochar.R
import com.mehedi.pokochar.repository.PokemonRepository
import com.mehedi.pokochar.repository.PokemonRepositoryImpl
import com.mehedi.pokochar.views.viewmodel.PokemonViewModel
import com.mehedi.pokochar.views.viewmodel.PokemonViewmodelFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {
    
    
    private val adapter: PokemonAdapter by lazy {
        PokemonAdapter(this)
        
    }
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        val pokemonRepo: PokemonRepository = PokemonRepositoryImpl(ApiClient.retrofit)
        val viewmodelFactory = PokemonViewmodelFactory(pokemonRepo)
        val viewModel: PokemonViewModel by viewModels { viewmodelFactory }
        
        
        
        viewModel.pokemonState.onEach { state ->
            when (state) {
                is DataState.Loading -> {
                    // Show loading indicator
                }
                
                is DataState.Success -> {
                    
                    val pokemonList = state.data
                    setUiData(pokemonList)
                }
                
                is DataState.Error -> {
                    // Show error message
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }.launchIn(lifecycleScope)
        
        viewModel.fetchPokemonList()
    }
    
    private fun setUiData(pokemonList: List<Pokemon>?) {
        pokemonList?.let { adapter.updatePokemonList(it) }
        val recyclerView = findViewById<RecyclerView>(R.id.pokemon_carousel)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = adapter
        
    }
}