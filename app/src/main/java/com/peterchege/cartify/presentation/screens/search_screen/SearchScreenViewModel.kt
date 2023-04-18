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

import android.content.Context
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.cartify.core.api.CartifyApi
import com.peterchege.cartify.domain.state.SearchProducts
import com.peterchege.cartify.domain.state.SearchProductsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val api: CartifyApi

) : ViewModel() {

    private val _searchTerm = mutableStateOf("")
    val searchTerm: State<String> = _searchTerm
    var searchJob: Job? = null

    val _uiState =
        MutableStateFlow<SearchProductsUiState>(SearchProductsUiState.Idle(message = "Search a product"))
    val uiState = _uiState.asStateFlow()


    fun onChangeSearchTerm(query: String) {
        _searchTerm.value = query
        if (query.length > 3) {
            _uiState.value = SearchProductsUiState.Loading(message = "Loading...")
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                try {
                    val response = api.searchProduct(searchTerm = query)

                    if (response.success) {
                        _uiState.value =
                            SearchProductsUiState.Success(data = SearchProducts(products = response.products))
                    } else {
                        _uiState.value = SearchProductsUiState.Error(errorMessage = response.msg)
                    }
                } catch (e: HttpException) {
                    _uiState.value = SearchProductsUiState.Error(
                        errorMessage =
                        e.localizedMessage ?: "Please check your internet connection"
                    )
                } catch (e: IOException) {
                    _uiState.value = SearchProductsUiState.Error(
                        errorMessage = e.localizedMessage ?: "An unexpected error occurred"
                    )
                }
            }
        }
    }
}