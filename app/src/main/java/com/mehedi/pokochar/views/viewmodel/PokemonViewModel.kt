package com.mehedi.pokochar.views.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehedi.pokochar.network.DataState
import com.mehedi.pokochar.network.Pokemon
import com.mehedi.pokochar.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {
    
    private val _pokemonState = MutableStateFlow<DataState<List<Pokemon>>>(DataState.Loading())
    val pokemonState: StateFlow<DataState<List<Pokemon>>> = _pokemonState.asStateFlow()
    
    fun fetchPokemonList() {
        viewModelScope.launch {
            val pokemonList = mutableListOf<Pokemon>()
            for (i in 1..151) {
                val pokemonDataState = pokemonRepository.getPokemon(i)
                
                pokemonDataState.collect { state ->
                    
                    when (state) {
                        is DataState.Error -> {}
                        is DataState.Loading -> {}
                        is DataState.Success -> state.data?.let { pokemonList.add(it) }
                    }
                    
                }
                
            }
            // Update state with the entire Pokemon list
            _pokemonState.value = DataState.Success(pokemonList)
        }
    }
}

