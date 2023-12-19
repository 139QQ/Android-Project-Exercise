package com.lzok.readmate.DataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lzok.readmate.item.NewsListItem


/**
 * NewListItem的增删改查操作
 */
@Dao
interface NewsListItemDao {
   /** 插入**/
   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertAll(newsItem:List<NewsListItem> )

   @Query("SELECT * FROM NewsListItem")
   suspend fun getAll(): List<NewsListItem>
   @Query("SELECT  title FROM NewsListItem")
   suspend fun getTitle(): List<String>
}

