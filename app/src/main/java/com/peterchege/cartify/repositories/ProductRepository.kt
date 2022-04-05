package com.peterchege.cartify.repositories

import com.peterchege.cartify.api.CartifyApi
import com.peterchege.cartify.api.responses.AllProductsResponse
import com.peterchege.cartify.api.responses.ProductByIdResponse
import com.peterchege.cartify.models.Product
import com.peterchege.cartify.models.toProductRoom
import com.peterchege.cartify.room.database.CartifyDatabase
import com.peterchege.cartify.room.entities.ProductRoom
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val api: CartifyApi,
    private val db: CartifyDatabase
) {
    suspend fun getAllProducts():AllProductsResponse{
        return api.getAllProducts()
    }
    suspend fun getProductById(id:String):ProductByIdResponse{
        return api.getProductById(id)

    }
    suspend fun addProductToWishList(product: Product){
        return db.productDao.insertProduct(productRoom = product.toProductRoom())
    }
    fun getWishListProducts(): Flow<List<ProductRoom>> {
        return db.productDao.getProducts()
    }
    suspend fun deleteWishListProductById(id:String){
        return db.productDao.deleteProductById(id)
    }
    suspend fun deleteAllWishListProducts(){
        return db.productDao.deleteAllProduct()
    }


}