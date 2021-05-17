package com.goddoro.module.presentation.retry.room

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single


/**
 * created By DORO 5/17/21
 */

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUploadRequest(item: ImageItem): Single<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUploadRequest(item: ImageItem): Completable

    @Delete
    fun deleteUploadRequest(vararg item: ImageItem): Completable

    @Query("SELECT * FROM ImageItem ORDER BY updateTimeMs DESC")
    fun listImages(): Single<List<ImageItem>>
//
//    @Query("SELECT * FROM ImageItem WHERE userId == :userId  ORDER BY updateTimeMs DESC")
//    fun listPendingRequest( userId : Int ): Single<List<ImageItem>>
}