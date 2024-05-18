package com.example.FirdaJetpack.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.FirdaJetpack.data.RepositoryAnimals
import com.example.FirdaJetpack.model.Animal
import com.example.FirdaJetpack.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: RepositoryAnimals
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Animal>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Animal>>
        get() = _uiState

    fun getAnimalById(id: Int) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(repository.getAnimalById(id))
    }


    fun updateAnimal(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateAnimal(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getAnimalById(id)
            }
    }

}