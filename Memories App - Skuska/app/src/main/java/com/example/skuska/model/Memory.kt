package com.example.skuska.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memories")
data class Memory(@ColumnInfo(name = "name") var name: String,
                  @ColumnInfo(name = "text") var text: String,
                  @ColumnInfo(name = "place") var place: String,
                  @ColumnInfo(name = "type") var type: Boolean?) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
}