package com.comtip.tip.mydictk.Control

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.comtip.tip.mydictk.Presenter.openChromeCustomTab
import com.comtip.tip.mydictk.R
import com.comtip.tip.mydictk.RoomDatabaseKotlin.VocabularyView
import java.util.*

/**
 * Created by TipRayong on 22/3/2561 16:35
 * MyDictK
 */


fun createRiddleWordsGame(activity: Activity, vocabularyView: VocabularyView) {

    val random = Random().nextInt(vocabularyView.allData().size)

    alertGame(activity, vocabularyView, vocabularyView.allData().get(random).vocab!!)
}

@SuppressLint("SetTextI18n")
private fun alertGame(activity: Activity, vocabularyView: VocabularyView, answer: String) {
    val dialogGame = Dialog(activity)
    dialogGame.setContentView(R.layout.game_layout)
    dialogGame.setTitle("Game")
    dialogGame.setCancelable(false)
    val random = Random()
    var quest = ""
    for (i in 0 until answer.length) {
        val rNumber = random.nextInt(2)
        quest = if (rNumber == 1) {
            quest + answer.substring(i, i + 1) + " "
        } else {
            quest + "_ "
        }
    }
    val questionTV = dialogGame.findViewById<TextView>(R.id.questionTV) as TextView
    questionTV.text = quest

    questionTV.setOnLongClickListener {
        Toast.makeText(activity, "Answer : $answer", Toast.LENGTH_SHORT).show()
        false
    }

    val answerET = dialogGame.findViewById<EditText>(R.id.answerET) as EditText
    val cancelBTN = dialogGame.findViewById<Button>(R.id.cancelBTN) as Button
    cancelBTN.setOnClickListener { dialogGame.dismiss() }

    val answerBTN = dialogGame.findViewById<Button>(R.id.answerBTN) as Button
    answerBTN.setOnClickListener {
        if (answerET.text.toString().equals(answer, true)) {
            questionTV.text = "$answer ✔✔✔"
            val translateGoogle = "https://translate.google.co.th/?source=osdd#auto/th/"
            openChromeCustomTab(activity, translateGoogle + answer)
        } else {
            Toast.makeText(activity, "Try Again !!!$answer", Toast.LENGTH_SHORT).show()
        }
    }
    val nextBTN = dialogGame.findViewById<Button>(R.id.nextBTN) as Button
    nextBTN.setOnClickListener {
        dialogGame.dismiss()
        createRiddleWordsGame(activity, vocabularyView)
    }
    dialogGame.show()
}