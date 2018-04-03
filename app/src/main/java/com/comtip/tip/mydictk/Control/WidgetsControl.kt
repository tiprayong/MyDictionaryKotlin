package com.comtip.tip.mydictk.Control


import android.app.Activity
import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.comtip.tip.mydictk.Presenter.setupChromeCustomTab
import com.comtip.tip.mydictk.Presenter.showAlphabetList
import com.comtip.tip.mydictk.Presenter.showVocabData
import com.comtip.tip.mydictk.RoomDatabaseKotlin.AppDatabase
import com.comtip.tip.mydictk.RoomDatabaseKotlin.BackupDatabase
import com.comtip.tip.mydictk.RoomDatabaseKotlin.Vocabulary
import com.comtip.tip.mydictk.RoomDatabaseKotlin.VocabularyView
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by TipRayong on 22/3/2561 12:37
 * MyDictK
 */


fun initialize(activity: Activity, vocabularyView: VocabularyView) {

    setupChromeCustomTab(activity)
    activity.vocabRV.layoutManager = LinearLayoutManager(activity)

    activity.recentBT.setOnClickListener({ showVocabData(activity, vocabularyView, vocabularyView.allData()) })

    activity.alphabetBT.setOnClickListener({ showAlphabetList(activity, vocabularyView) })

    activity.searchBT.setOnClickListener({
        if (activity.vocabET.text.toString().isEmpty()) {
            Toast.makeText(activity, "Empty!!!", Toast.LENGTH_SHORT).show()
        } else {
            prepareSearchVocabulary(activity, vocabularyView, activity.vocabET.text.toString())
        }
    })

    activity.addBT.setOnClickListener({
        if (activity.vocabET.text.toString().isEmpty()) {
            Toast.makeText(activity, "Empty!!!", Toast.LENGTH_SHORT).show()
        } else {
            prepareAddVocabulary(activity, vocabularyView, activity.vocabET.text.toString())
        }
    })

    activity.backupTV.setOnClickListener({ alertBackup(activity, vocabularyView, AppDatabase.DATABASE)})
}

private fun prepareAddVocabulary(activity: Activity, vocabularyView: VocabularyView, tempVocab: String) {
    val tempAlphabet = tempVocab.substring(0, 1)
    if ((tempAlphabet.equals(" ", true)) || (tempAlphabet.equals("'", true))) {
        Toast.makeText(activity, "Can't Add Vocabulary!!! Please Check.", Toast.LENGTH_SHORT).show()
    } else {
        val vocabulary = Vocabulary()
        vocabulary.vocab = tempVocab
        vocabulary.alphabet = tempAlphabet.toUpperCase()
        vocabularyView.addVocabulary(vocabulary)
        hideKeyboard(activity)
        Toast.makeText(activity, "Add $tempVocab", Toast.LENGTH_SHORT).show()
    }
}


private fun prepareSearchVocabulary(activity: Activity, vocabularyView: VocabularyView, searchVocab: String) {
    showVocabData(activity, vocabularyView, vocabularyView.searchData(searchVocab))
}

private fun hideKeyboard(activity: Activity) {
    val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(activity.vocabET.windowToken, 0)
}


private fun alertBackup(activity: Activity, vocabularyView: VocabularyView, database: String) {
    val alertBackup = AlertDialog.Builder(activity)
    alertBackup.setTitle("Backup")

    alertBackup.setPositiveButton("Backup") { _, _ -> BackupDatabase.backup(activity, database) }

    alertBackup.setNegativeButton("Load") { _, _ ->
        if (BackupDatabase.restore(activity, database)) {
            showVocabData(activity,
                    vocabularyView,
                    vocabularyView.allData()
            )
        }
    }
    val alertB = alertBackup.create()
    alertB.show()
}

