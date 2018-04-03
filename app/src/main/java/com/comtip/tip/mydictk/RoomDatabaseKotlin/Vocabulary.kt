package com.comtip.tip.mydictk.RoomDatabaseKotlin

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by TipRayong on 21/3/2561 15:17
 * MyDictK
 */
@Entity
data class Vocabulary(
        @PrimaryKey(autoGenerate = true)var id:Int? = null,
        var vocab:String? = null,
        var alphabet:String? = null)