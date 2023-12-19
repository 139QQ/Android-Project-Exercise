package com.lzok.readmate.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemDataEntity (
    @PrimaryKey var id:Int,
    @ColumnInfo(name = "Titel") val title: String?,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "pubDate") val pubDate: String?,
    @ColumnInfo(name = "content") val content: String?
)