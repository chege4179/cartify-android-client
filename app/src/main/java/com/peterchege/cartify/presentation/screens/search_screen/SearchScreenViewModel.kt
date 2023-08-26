/*
 * Copyright 2023 Cartify By Peter Chege
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peterchege.cartify.presentation.screens.search_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.cartify.core.api.NetworkResult
import com.peterchege.cartify.domain.models.Product
import com.peterchege.cartify.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface SearchProductScreensUiState {
    object Idle : SearchProductScreensUiState

    data class ResultsFound(val products: List<Product>) : SearchProductScreensUiState

    object Searching : SearchProductScreensUiState

    data class Error(val message: String) : SearchProductScreensUiState

}

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val productRepository: ProductRepository,

    ) : ViewModel() {

    private val _searchTerm = mutableStateOf("")
    val searchTerm: State<String> = _searchTerm
    var searchJob: Job? = null

    val _uiState =
        MutableStateFlow<SearchProductScreensUiState>(SearchProductScreensUiState.Idle)
    val uiState = _uiState.asStateFlow()


    fun onChangeSearchTerm(query: String) {
        _searchTerm.value = query
        if (query.length > 3) {
            _uiState.value = SearchProductScreensUiState.Searching
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                val response = productRepository.searchProducts(query = query)
                when (response) {
                    is NetworkResult.Success -> {
                        if (response.data.success) {
                            _uiState.value =
                                SearchProductScreensUiState.ResultsFound(
                                    products = response.data.products
                                )
                        } else {
                            _uiState.value =
                                SearchProductScreensUiState.Error(message = response.data.msg)
                        }
                    }

                    is NetworkResult.Error -> {
                        _uiState.value = SearchProductScreensUiState.Error(
                            message = "Please check your internet connection"
                        )
                    }

                    is NetworkResult.Exception -> {
                        _uiState.value = SearchProductScreensUiState.Error(
                            message = "An unexpected error occurred"
                        )
                    }
                }
            }
        }
    }
}