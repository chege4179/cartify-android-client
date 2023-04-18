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
package com.peterchege.cartify.domain.state

import com.peterchege.cartify.domain.models.Product
import com.skydoves.sealedx.core.Extensive
import com.skydoves.sealedx.core.annotations.ExtensiveModel
import com.skydoves.sealedx.core.annotations.ExtensiveSealed

@ExtensiveSealed(
    models = [
        ExtensiveModel(Products::class),
        ExtensiveModel(SearchProducts::class)

    ]
)
sealed interface UiState {
    data class Idle (val message:String):UiState
    data class Success(val data: Extensive) : UiState
    data class Loading(val message:String) : UiState
    data class Error (val errorMessage:String): UiState
}

data class Products(
    val products:List<Product>
)

data class SearchProducts(
    val products:List<Product>
)

