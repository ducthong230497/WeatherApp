package com.weather.app.data.database.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(obj: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertAll(obj: List<T>): Array<Long>

    @Update
    abstract suspend fun update(obj: T): Int

    @Update
    abstract suspend fun updateAll(obj: List<T>): Int

    @Delete
    abstract suspend fun delete(obj: T): Int

    open suspend fun upsert(obj: T): Int {
        return upsert(obj, { insert(it) }, { update(it) })
    }

    open suspend fun upsertAll(objList: List<T>): Int {
        return upsertAll(objList, { insertAll(it) }, { updateAll(it) })
    }

    /**
     * Common method
     */
    suspend fun <Item> upsert(
        obj: Item,
        insertCallable: suspend (obj: Item) -> Long,
        updateCallable: suspend (obj: Item) -> Int
    ): Int {
        val id = insertCallable.invoke(obj)
        if (id == -1L) {
            return updateCallable.invoke(obj)
        }
        return 1
    }

    suspend fun <Item> upsertAll(
        objList: List<Item>,
        insertCallable: suspend (objList: List<Item>) -> Array<Long>,
        updateCallable: suspend (objList: List<Item>) -> Int
    ): Int {
        var successful = 0
        val insertResult = insertCallable.invoke(objList)
        val updateList: MutableList<Item> = ArrayList()
        for (i in insertResult.indices) {
            if (insertResult[i] == -1L) {
                updateList.add(objList[i])
            } else {
                successful++
            }
        }
        if (updateList.isNotEmpty()) {
            successful += updateCallable.invoke(updateList)
        }
        return successful
    }
}
