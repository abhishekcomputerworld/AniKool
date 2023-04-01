package com.example.anukoolfinal.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppDao {
  /*  @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(redCliffeCity: AppEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(redCliffeCity: List<AppEntity>)

    @Query("DELETE FROM city_table")
    fun deleteAll()

    @Query("SELECT * from city_table  ORDER by name ASC")
    fun getRedCliffeCities(): LiveData<List<AppEntity>>?

    @Query("SELECT * FROM city_table WHERE name = :name")
    fun getCityDetails(name : String?) : LiveData<AppEntity>*/
}