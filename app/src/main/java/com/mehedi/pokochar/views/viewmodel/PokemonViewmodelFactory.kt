package com.mehedi.pokochar.views.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mehedi.pokochar.repository.PokemonRepository

class PokemonViewmodelFactory(private val repository: PokemonRepository) :
    ViewModelProvider.Factory {
    
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PokemonViewModel::class.java)) {
            
            PokemonViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
    
    
}