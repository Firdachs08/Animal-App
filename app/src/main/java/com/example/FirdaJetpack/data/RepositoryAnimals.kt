package com.example.FirdaJetpack.data

import com.example.FirdaJetpack.model.DataAnimal
import com.example.FirdaJetpack.model.Animal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class RepositoryAnimals {
    private val dummyAnimal = mutableListOf<Animal>()

    init {
        if (dummyAnimal.isEmpty()) {
            DataAnimal.dummyAnimals.forEach {
                dummyAnimal.add(it)
            }
        }
    }

    fun getAnimalById(animalId: Int): Animal {
        return dummyAnimal.first {
            it.id == animalId
        }
    }

    fun getFavoriteAnimal(): Flow<List<Animal>> {
        return flowOf(dummyAnimal.filter { it.isFavorite })
    }

    fun searchAnimal(query: String) = flow {
        val data = dummyAnimal.filter {
            it.name.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun updateAnimal(AnimalId: Int, newState: Boolean): Flow<Boolean> {
        val index = dummyAnimal.indexOfFirst { it.id == AnimalId }
        val result = if (index >= 0) {
            val animal = dummyAnimal[index]
            dummyAnimal[index] = animal.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: RepositoryAnimals? = null

        fun getInstance(): RepositoryAnimals =
            instance ?: synchronized(this) {
                RepositoryAnimals().apply {
                    instance = this
                }
            }
    }
}