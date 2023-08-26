package com.peterchege.cartify.data.local.saved_products

import com.peterchege.cartify.core.di.IoDispatcher
import com.peterchege.cartify.core.room.dao.SavedProductDao
import com.peterchege.cartify.core.room.database.CartifyDatabase
import com.peterchege.cartify.core.room.entities.SavedProductEntity
import com.peterchege.cartify.domain.mappers.toSavedProductEntity
import com.peterchege.cartify.domain.models.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface SavedProductsDataSource {
    suspend fun addProductToWishList(product: Product)

    fun getWishListProducts(): Flow<List<SavedProductEntity>>

    suspend fun deleteWishListProductById(id:String)

    suspend fun deleteAllWishListProducts()
}


class SavedProductsDataSourceImpl @Inject constructor(
    private val db:CartifyDatabase,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
):SavedProductsDataSource{
    override suspend fun addProductToWishList(product: Product){
        return db.savedProductDao.insertSavedProduct(product.toSavedProductEntity())
    }
    override fun getWishListProducts(): Flow<List<SavedProductEntity>> {
        return db.savedProductDao.getAllSavedProducts().flowOn(ioDispatcher)
    }
    override suspend fun deleteWishListProductById(id:String){
        return db.savedProductDao.deleteSavedProductById(id)
    }
    override suspend fun deleteAllWishListProducts(){
        return db.savedProductDao.deleteAllSavedProducts()
    }
}