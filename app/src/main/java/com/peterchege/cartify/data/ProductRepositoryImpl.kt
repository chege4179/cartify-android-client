package com.peterchege.cartify.data

import com.peterchege.cartify.core.api.CartifyApi
import com.peterchege.cartify.core.api.responses.AllProductsResponse
import com.peterchege.cartify.core.api.responses.ProductByIdResponse
import com.peterchege.cartify.domain.models.Product
import com.peterchege.cartify.core.room.database.CartifyDatabase
import com.peterchege.cartify.core.room.entities.ProductRoom
import com.peterchege.cartify.domain.mappers.toProductRoom
import com.peterchege.cartify.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: CartifyApi,
    private val db: CartifyDatabase
):ProductRepository {
    override suspend fun getAllProducts(): AllProductsResponse {
        return api.getAllProducts()
    }
    override suspend fun getProductById(id:String): ProductByIdResponse {
        return api.getProductById(id)

    }
    override suspend fun addProductToWishList(product: Product){
        return db.productDao.insertProduct(productRoom = product.toProductRoom())
    }
    override fun getWishListProducts(): Flow<List<ProductRoom>> {
        return db.productDao.getProducts()
    }
    override suspend fun deleteWishListProductById(id:String){
        return db.productDao.deleteProductById(id)
    }
    override suspend fun deleteAllWishListProducts(){
        return db.productDao.deleteAllProduct()
    }


}