package com.mehedi.pokochar.repository

import com.mehedi.pokochar.network.DataState
import com.mehedi.pokochar.network.Pokemon
import com.mehedi.pokochar.network.PokemonService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import retrofit2.Retrofit

class PokemonRepositoryImpl(private val retrofit: Retrofit) : PokemonRepository {
    
    private val pokemonService: PokemonService by lazy {
        retrofit.create(PokemonService::class.java)
    }
    
    override suspend fun getPokemon(id: Int): Flow<DataState<Pokemon>> = flow {
        val response = try {
            pokemonService.getPokemon(id)
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
            throw e
        }
        if (response.isSuccessful) {
            emit(DataState.Success(response.body()))
        }
        
        
    }
}