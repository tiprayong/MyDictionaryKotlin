package com.comtip.tip.mydictk

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.comtip.tip.mydictk.Control.initialize
import com.comtip.tip.mydictk.Presenter.showVocabData
import com.comtip.tip.mydictk.Presenter.unbindChromeCustomTab
import com.comtip.tip.mydictk.RoomDatabaseKotlin.Vocabulary
import com.comtip.tip.mydictk.RoomDatabaseKotlin.VocabularyView
import com.comtip.tip.mydictk.RoomDatabaseKotlin.vocabLive

class MainActivity : AppCompatActivity() {

    lateinit var vocabularyView: VocabularyView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vocabularyView = ViewModelProviders.of(this).get(VocabularyView::class.java)

        initialize(this, vocabularyView)

        subScribe()

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }
    }


    private fun subScribe() {
        vocabLive!!.observe(this, Observer<List<Vocabulary>>
        { t -> showVocabData(this@MainActivity, vocabularyView, t!!) })
    }


    override fun onDestroy() {
        super.onDestroy()
        unbindChromeCustomTab(this)
        vocabularyView.closeRoom()
    }
}

