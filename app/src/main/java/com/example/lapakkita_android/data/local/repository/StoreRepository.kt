package com.example.lapakkita_android.data.local.repository

import android.util.Log
import com.example.lapakkita_android.data.local.entity.StoreEntity
import com.example.lapakkita_android.data.local.dao.StoreDao
import com.example.lapakkita_android.data.remote.response.AllStoreResponseItem
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class StoreRepository private constructor(
 private val storeDao: StoreDao
){

    val fakeData = mutableListOf<AllStoreResponseItem>()
    private var storeData = mutableListOf<StoreEntity>()
    val db = Firebase.database
    private val dbReference = Firebase.database.getReference("listStore")

    private suspend fun getData(): MutableList<AllStoreResponseItem> = suspendCancellableCoroutine { continuation ->
        val valueListener = object : ValueEventListener {
            val storeList = mutableListOf<AllStoreResponseItem>()
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    storeList.add(it.getValue<AllStoreResponseItem>()!!)
                }
                continuation.resume(storeList)
            }

            override fun onCancelled(error: DatabaseError) {
                continuation.cancel()
            }
        }
        dbReference.addValueEventListener(valueListener)

        continuation.invokeOnCancellation {
            dbReference.removeEventListener(valueListener)
        }
    }

    suspend fun getStoreList() : Flow<List<StoreEntity>> {
        try {
            getData().forEach {
                val isBookmark = storeDao.isFavorite(it.id_toko)
                storeData.add(
                    StoreEntity(
                        it.id_toko,
                        it.nama_toko,
                        it.deskripsi,
                        it.url_image,
                        it.rerata_rating,
                        it.jumlah_rating,
                        it.latitude,
                        it.longitude,
                        isBookmark,
                        it.kategori
                    )
                )
            }
            storeDao.deleteAll()
            storeDao.insertStore(storeData)
        }catch (e: Exception){
            Log.e("ERR", "error")
        }

        return flowOf(storeDao.getStore())
    }

    suspend fun getDetail(id: Int): Flow<StoreEntity?>{
        var storeDetail: StoreEntity? = null
        try{
            getData().forEach {
                if(it.id_toko == id){
                    val isBookmark = storeDao.isFavorite(it.id_toko)
                    storeDetail = StoreEntity(
                        it.id_toko,
                        it.nama_toko,
                        it.deskripsi,
                        it.url_image,
                        it.rerata_rating,
                        it.jumlah_rating,
                        it.latitude,
                        it.longitude,
                        isBookmark,
                        it.kategori
                    )
                }
            }
        }catch (e: Exception){
            Log.e("ERR", e.message.toString())
        }

        return flowOf(storeDetail)
    }

    suspend fun getBookmarkList(): Flow<List<StoreEntity>> {
        return flowOf(
            storeDao.getStore().filter {
                it.isBookmarked
            }
        )
    }

    suspend fun setBookmarkItem(item: StoreEntity, added: Boolean, location: String){
        item.isBookmarked = added
        item.location = location
        storeDao.updateStore(item)
    }

    suspend fun searchStore(query: String) : Flow<List<StoreEntity>>{
        val storeList = storeDao.getStore()
        return if(query != "") {
            flowOf(storeList.filter {
                it.name.contains(
                    query,
                    ignoreCase = true
                )
            })
        }else{
            flowOf(storeList)
        }
    }

    suspend fun searchStoreById(id: Int) : Flow<StoreEntity>{
        val storeList = storeDao.getStore()
        lateinit var selectedStore: StoreEntity
        storeList.forEach {
            if(it.id == id)
                selectedStore = it
        }
        return flowOf(selectedStore)
    }

    suspend fun searchStoreByCategoryAndName(name: String, category: String): Flow<List<StoreEntity>> {
        val storeList = storeDao.getStore()
        return if(category.isEmpty()){
            if(name.isEmpty()){
                flowOf(storeList)
            }
            else{
                flowOf(storeList.filter {
                    it.name.contains(
                        name,
                        ignoreCase = true
                    )
                })
            }
        }
        else{
            if(name.isEmpty()){
                flowOf(storeList.filter {
                    it.category.contains(
                        category,
                        ignoreCase = true
                    )
                })
            }
            else{
                flowOf(storeList.filter {
                    it.name.contains(
                        name,
                        ignoreCase = true
                    )
                }.filter {
                    it.category.contains(
                        category,
                        ignoreCase = true
                    )
                })
            }
        }
    }

    companion object {
        @Volatile
        private var instance: StoreRepository? = null
        fun getInstance(
//            apiService: ApiService,
            storeDao: StoreDao
        ): StoreRepository =
            instance ?: synchronized(this) {
                instance ?: StoreRepository(
//                    apiService,
                    storeDao,
                )
            }.also { instance = it }
    }
}