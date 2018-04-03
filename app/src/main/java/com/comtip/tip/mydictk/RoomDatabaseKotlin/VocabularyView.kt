package com.comtip.tip.mydictk.RoomDatabaseKotlin

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

/**
 * Created by TipRayong on 21/3/2561 15:33
 * MyDictK
 */


var vocabLive: LiveData<List<Vocabulary>>? = null

class VocabularyView(application: Application) : AndroidViewModel(application) {

    private val mDb = AppDatabase.getInDatabase(application)

    init {
        fetchLiveData()
    }

    fun closeRoom() {
        mDb.close()
    }

    //------ Fetch Live------------
    private fun fetchLiveData() {
        try {
            val fetchTask = FetchVocabLiveAsyncTask(mDb)
            vocabLive = fetchTask.execute().get()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private class FetchVocabLiveAsyncTask internal constructor(db: AppDatabase) : AsyncTask<Void, Void, LiveData<List<Vocabulary>>>() {
        private val DB: AppDatabase = db


        protected override fun doInBackground(vararg voids: Void): LiveData<List<Vocabulary>>? {
            return DB.vocabularyModel().showVocabLive()
        }
    }
    //---------------------------


    //----Search Vocabulary-----
    fun searchData(searchVocab: String): List<Vocabulary> {
        var searchData = ArrayList<Vocabulary>()
        try {
            val searchTask = SearchVocabLiveAsyncTask(mDb, searchVocab)
            searchData = searchTask.execute().get() as ArrayList<Vocabulary>
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return searchData
    }

    private class SearchVocabLiveAsyncTask internal constructor(db: AppDatabase, searchVocab: String) : AsyncTask<Void, Void, List<Vocabulary>>() {
        private val DB: AppDatabase = db
        private val search: String = searchVocab

        override fun doInBackground(vararg voids: Void): List<Vocabulary> {
            return DB.vocabularyModel().searchVocab(search)
        }
    }
    //---------------------------


    //------ Show All Data------------
    fun allData(): List<Vocabulary> {
        var data = ArrayList<Vocabulary>()
        try {
            val fetchTask = FetchVocabAsyncTask(mDb)
            data = fetchTask.execute().get() as ArrayList<Vocabulary>
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return data
    }

    private class FetchVocabAsyncTask internal constructor(db: AppDatabase) : AsyncTask<Void, Void, List<Vocabulary>>() {
        private val DB: AppDatabase = db

        override fun doInBackground(vararg voids: Void): List<Vocabulary> {
            return DB.vocabularyModel().showAllVocab()
        }
    }
    //---------------------------


    //--- Add Data -----------------------
    fun addVocabulary(vocabulary: Vocabulary) {
        val addTask = AddVocabAsyncTask(mDb, vocabulary)
        addTask.execute()
    }

    private class AddVocabAsyncTask internal constructor(db: AppDatabase, vocabulary: Vocabulary) : AsyncTask<Void, Void, Void>() {
        private val DB: AppDatabase = db
        private val addVC: Vocabulary = vocabulary

        protected override fun doInBackground(vararg voids: Void): Void? {
            DB.vocabularyModel().insertVocab(addVC)
            return null
        }
    }
    //---------------------------


    //-----Edit Data-----
    fun editVocabulary(id: Int, eVocab: String, eAlpha: String) {
        val editTask = EditVocabAsyncTask(mDb, id, eVocab, eAlpha)
        editTask.execute()
    }

    private class EditVocabAsyncTask internal constructor(db: AppDatabase, id: Int, eVocab: String, eAlphabet: String) : AsyncTask<Void, Void, Void>() {
        private val DB: AppDatabase = db
        private var id: Int = 0
        private val eVocab: String
        private val eAlphabet: String

        init {
            this.id = id
            this.eVocab = eVocab
            this.eAlphabet = eAlphabet
        }

        override fun doInBackground(vararg voids: Void): Void? {
            DB.vocabularyModel().editVocab(id, eVocab, eAlphabet)
            return null
        }
    }
    //---------------------------


    //-----Delete Data-----
    fun deleteVocabulary(id: Int) {
        val deleteTask = DeleteVocabAsyncTask(mDb, id)
        deleteTask.execute()
    }

    private class DeleteVocabAsyncTask internal constructor(db: AppDatabase, id: Int) : AsyncTask<Void, Void, Void>() {
        private val DB: AppDatabase = db
        private var id: Int = 0

        init {
            this.id = id
        }

        protected override fun doInBackground(vararg voids: Void): Void? {
            DB.vocabularyModel().deleteVocab(id)
            return null
        }
    }
    //---------------------------
}