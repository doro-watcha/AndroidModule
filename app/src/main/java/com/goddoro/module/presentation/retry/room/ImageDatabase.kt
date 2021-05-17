package com.goddoro.module.presentation.retry.room

import androidx.room.Database
import androidx.room.RoomDatabase


/**
 * created By DORO 5/17/21
 */

@Database(entities = [ImageItem::class], version = 1)
abstract class ImageDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}