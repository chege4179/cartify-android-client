package com.peterchege.cartify.room.dao

import androidx.room.*

import com.peterchege.cartify.room.entities.ProductRoom

import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getProducts(): Flow<List<ProductRoom>>

    @Query("SELECT * FROM product WHERE _id = :id")
    suspend fun getProductById(id: String): ProductRoom?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productRoom: ProductRoom)

    @Query("DELETE FROM product WHERE _id = :id")
    suspend fun deleteProductById(id: String)

    @Query("DELETE FROM product")
    suspend fun deleteAllProduct()
}