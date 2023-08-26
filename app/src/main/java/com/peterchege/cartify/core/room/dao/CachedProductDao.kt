package com.peterchege.cartify.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peterchege.cartify.core.room.entities.CachedProductEntity
import com.peterchege.cartify.core.room.entities.SavedProductEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CachedProductDao {

    @Query("SELECT * FROM cached_product")
    fun getAllCachedProducts(): Flow<List<CachedProductEntity>>

    @Query("SELECT * FROM cached_product WHERE _id = :id")
    fun getCachedProductById(id: String):Flow<CachedProductEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCachedProduct(product: CachedProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCachedProducts(products: List<CachedProductEntity>)

    @Query("DELETE FROM cached_product WHERE _id = :id")
    suspend fun deleteCachedProductById(id: String)

    @Query("DELETE FROM saved_product")
    suspend fun deleteAllCachedProducts()
}