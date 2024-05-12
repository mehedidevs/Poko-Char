package com.mehedi.pokochar.repository

import com.mehedi.pokochar.network.DataState
import com.mehedi.pokochar.network.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getPokemon(id: Int): Flow<DataState<Pokemon>>
}