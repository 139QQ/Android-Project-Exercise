package com.lzok.readmate.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lzok.readmate.item.NewsListItem


@Database(entities = [NewsListItem::class], version = 1, exportSchema = false)
abstract class RssHub : RoomDatabase() {
    abstract fun ItemDao(): NewsListItemDao

    companion object {
        @Volatile
        private var instance: RssHub? = null

        fun getInstance(context: Context): RssHub {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): RssHub {
            return Room.databaseBuilder(context, RssHub::class.java, "app_database")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
