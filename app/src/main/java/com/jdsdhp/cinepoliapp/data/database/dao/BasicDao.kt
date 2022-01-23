package com.jdsdhp.cinepoliapp.data.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BasicDao<M> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: M)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(models: List<M>)

    @Update
    fun update(model: M)

    @Update
    fun update(models: List<M>)

    @Delete
    fun delete(model: M)

}