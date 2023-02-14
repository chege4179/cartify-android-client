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
package com.peterchege.cartify.domain.use_cases

import com.peterchege.cartify.core.api.responses.AllProductsResponse
import com.peterchege.cartify.core.util.Resource
import com.peterchege.cartify.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(): Flow<Resource<AllProductsResponse>> = flow{
        try {
            emit(Resource.Loading())
            val response = productRepository.getAllProducts()
            emit(Resource.Success(data = response))
        }catch (e:HttpException){
            emit(Resource.Error(message = "An unexpected error occurred"))
        }catch (e:IOException){
            emit(Resource.Error(message = "An unexpected error occurred"))
        }
    }


}