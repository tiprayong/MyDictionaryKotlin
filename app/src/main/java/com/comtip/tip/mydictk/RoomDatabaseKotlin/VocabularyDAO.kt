package com.comtip.tip.mydictk.RoomDatabaseKotlin

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import android.arch.persistence.room.Query


/**
 * Created by TipRayong on 24/3/2561 13:23
 * MyDictK
 */
@Dao
interface VocabularyDAO {
    @Query("SELECT * FROM Vocabulary ORDER BY id DESC")
    fun showVocabLive():LiveData<List<Vocabulary>>

    @Query("SELECT * FROM Vocabulary ORDER BY id DESC")
    fun showAllVocab():List<Vocabulary>

    @Query("SELECT * FROM Vocabulary WHERE vocab LIKE :vocab||'%' ORDER BY vocab ASC")
    fun searchVocab(vocab:String):List<Vocabulary>

    @Insert(onConflict = IGNORE)
    fun insertVocab(vocabulary: Vocabulary)

    @Query("update Vocabulary set vocab = :newVocab , alphabet = :newAlphabet where id = :id")
    fun editVocab(id:Int,newVocab:String,newAlphabet:String)

    @Query("delete from Vocabulary where id = :id")
    fun deleteVocab(id:Int)
}
