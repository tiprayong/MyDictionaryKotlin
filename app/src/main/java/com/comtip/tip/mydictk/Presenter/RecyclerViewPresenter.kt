package com.comtip.tip.mydictk.Presenter

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.comtip.tip.mydictk.Control.createRiddleWordsGame
import com.comtip.tip.mydictk.R
import com.comtip.tip.mydictk.RoomDatabaseKotlin.Vocabulary
import com.comtip.tip.mydictk.RoomDatabaseKotlin.VocabularyView
import com.comtip.tip.weblogkt01.CustomRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by TipRayong on 22/3/2561 13:25
 * MyDictK
 */

fun showVocabData(activity: Activity, vocabularyView: VocabularyView, vocabData: List<Vocabulary>) {

    if (vocabData.isEmpty()) {
        Toast.makeText(activity, "Empty !!!", Toast.LENGTH_SHORT).show()
    }

    val adapter = CustomRecyclerViewAdapter(activity, vocabData)
    adapter.onItemClickListener = object : CustomRecyclerViewAdapter.OnItemClickListener {
        override fun onItemClick(position: Int) {
            val translateGoogle = "https://translate.google.co.th/?source=osdd#auto/th/"
            openChromeCustomTab(activity, translateGoogle + vocabData.get(position).vocab)
        }
    }

    adapter.onItemLongClickListener = object : CustomRecyclerViewAdapter.OnItemLongClickListener {
        override fun onItemLongClick(position: Int) {
            selectVocab(activity, vocabularyView, vocabData.get(position));
        }
    }
    activity.vocabRV.adapter = adapter
}


private fun selectVocab(activity: Activity, vocabularyView: VocabularyView, tempVocab: Vocabulary) {
    val alertSelect = AlertDialog.Builder(activity)
    alertSelect.setTitle("Please Select")
    alertSelect.setPositiveButton("✔ Edit ✔") { _, _ ->
        alertEdit(
                activity, vocabularyView, tempVocab
        )
    }

    alertSelect.setNegativeButton("✘ Delete ✘") { _, _ ->
        deleteVocab(
                activity, vocabularyView, tempVocab
        )
    }

    val alertS = alertSelect.create()
    alertS.show()
}


private fun alertEdit(activity: Activity, vocabularyView: VocabularyView, editVocab: Vocabulary) {
    val dialogEdit = Dialog(activity)
    dialogEdit.setContentView(R.layout.editlayout)
    dialogEdit.setTitle("Edit Vocabulary")
    dialogEdit.setCancelable(false)

    val editET = dialogEdit.findViewById<EditText>(R.id.editET) as EditText
    editET.setText(editVocab.vocab)

    val cancelBTN = dialogEdit.findViewById<Button>(R.id.cancelBTN) as Button
    cancelBTN.setOnClickListener { dialogEdit.dismiss() }

    val changeBTN = dialogEdit.findViewById<Button>(R.id.changeBTN) as Button
    changeBTN.setOnClickListener {
        if (!editET.text.toString().isEmpty()) {
            dialogEdit.dismiss()
            val bufferVocab = editET.text.toString()
            var bufferAlphabet = bufferVocab.substring(0, 1)
            if ((bufferAlphabet.equals(" ", ignoreCase = true)) || (bufferAlphabet.equals("'", ignoreCase = true))) {
                Toast.makeText(activity, "Can't Add Vocabulary!!! Please Check.", Toast.LENGTH_SHORT).show()
            } else {
                bufferAlphabet = bufferAlphabet.toUpperCase()
                vocabularyView.editVocabulary(editVocab.id!!, bufferVocab, bufferAlphabet)
                val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(editET.windowToken, 0)
                Toast.makeText(activity, "Edit Success $bufferAlphabet / $bufferVocab", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(activity, "Empty !!!", Toast.LENGTH_SHORT).show()
        }
    }

    dialogEdit.show()
}


private fun deleteVocab(activity: Activity, vocabularyView: VocabularyView, delVocab: Vocabulary) {
    val alertDelete = AlertDialog.Builder(activity)

    alertDelete.setTitle("Delete " + delVocab.vocab + " ?")

    alertDelete.setPositiveButton("✔ Yes ✔") { _, _ ->
        vocabularyView.deleteVocabulary(delVocab.id!!)
        Toast.makeText(activity, "Delete : " + delVocab.vocab, Toast.LENGTH_SHORT).show()
    }

    alertDelete.setNegativeButton("✘ No ✘") { _, _ -> }

    val alertD = alertDelete.create()
    alertD.show()
}


fun alphabetList(): List<Vocabulary> {
    val alpha = ArrayList<Vocabulary>()
    val alphabetList = arrayListOf(
            "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P",
            "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")

    for (item in alphabetList) {
        val vocab = Vocabulary()
        vocab.alphabet = item
        vocab.vocab = item
        alpha.add(vocab)
    }
    return alpha
}


fun showAlphabetList(activity: Activity, vocabularyView: VocabularyView) {
    val adapter = CustomRecyclerViewAdapter(activity, alphabetList())
    adapter.onItemClickListener = object : CustomRecyclerViewAdapter.OnItemClickListener {
        override fun onItemClick(position: Int) {
            showVocabData(activity, vocabularyView, vocabularyView.searchData(alphabetList()[position].alphabet!!))
        }
    }

    adapter.onItemLongClickListener = object : CustomRecyclerViewAdapter.OnItemLongClickListener {
        override fun onItemLongClick(position: Int) {
            createRiddleWordsGame(activity, vocabularyView)
            vocabularyView.searchData("abcdefghijklmnopqrstuvwxyz")
        }
    }
    activity.vocabRV.adapter = adapter
}


