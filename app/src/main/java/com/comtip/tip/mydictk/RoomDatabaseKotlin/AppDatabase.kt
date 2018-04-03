package com.comtip.tip.mydictk.RoomDatabaseKotlin

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * Created by TipRayong on 21/3/2561 15:34
 * MyDictK
 */

@Database(entities = arrayOf(Vocabulary::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vocabularyModel(): VocabularyDAO

    companion object {
        const val DATABASE = "vocabKDB"

        private var INSTANCE: AppDatabase? = null

        fun getInDatabase(context: Context): AppDatabase {

            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java,
                        DATABASE)
                        .build()
            }

            return INSTANCE as AppDatabase

        }
    }
}
