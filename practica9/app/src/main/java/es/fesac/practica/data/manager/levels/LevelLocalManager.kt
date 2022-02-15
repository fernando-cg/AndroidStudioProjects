package es.fesac.practica.data.manager.levels

import android.util.Log
import androidx.room.Room
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.fesac.practica.common.COLLECTION_LEVELS
import es.fesac.practica.common.DB_NAME
import es.fesac.practica.common.MyApplication
import es.fesac.practica.local.MyDatabase
import es.fesac.practica.local.mapper.toBo
import es.fesac.practica.local.mapper.toEntity
import es.fesac.practica.model.LevelBo
import kotlinx.coroutines.tasks.await

/**
 * TODO 9.4: Implementar el siguiente método:
 * - getLevels(): List<LevelBo>?
 * - saveLevelList(boList: List<LevelBo>)
 */
object LevelLocalManager{
    suspend fun getLevels():List<LevelBo>?{
        return try{
            val db = Room.databaseBuilder(
                MyApplication.instance,
                MyDatabase::class.java,
                DB_NAME).build()
            val levelList = db.getLevelDao().getAllLevelList()
            levelList.map { entity ->
                entity.toBo()
            }

        }catch (exception: Exception){
            Log.e("excepcion","Se ha producido un error")
            null
        }
    }

    suspend fun saveLevelList(boList:List<LevelBo>){
        val db = Room.databaseBuilder(
            MyApplication.instance,
            MyDatabase::class.java,
            DB_NAME).build()

        boList.let {
            db.getLevelDao().insertLevels(levelList = it.map { bo ->
                bo.toEntity()
            })
        }
    }
}