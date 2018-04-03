package com.comtip.tip.mydictk.Presenter

import android.content.ComponentName
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.support.customtabs.CustomTabsClient
import android.support.customtabs.CustomTabsIntent
import android.support.customtabs.CustomTabsServiceConnection
import android.support.customtabs.CustomTabsSession

/**
 * Created by TipRayong on 3/4/2561 13:21
 * MyDictK
 */
private var customTabsServiceConnection: CustomTabsServiceConnection? = null
private var customTabsIntent: CustomTabsIntent? = null

fun openChromeCustomTab(context: Context, url: String) {
    customTabsIntent!!.launchUrl(context, Uri.parse(url))
}

fun unbindChromeCustomTab(context: Context) {
    if (customTabsServiceConnection != null) {
        context.unbindService(customTabsServiceConnection)
    }
}

fun setupChromeCustomTab(context: Context) {
    var customTabsClient: CustomTabsClient?
    var customTabsSession: CustomTabsSession? = null
    val packageName = "com.android.chrome"

    customTabsServiceConnection = object : CustomTabsServiceConnection() {
        override fun onServiceDisconnected(p0: ComponentName?) {
            customTabsClient = null
        }

        override fun onCustomTabsServiceConnected(name: ComponentName?, client: CustomTabsClient?) {
            customTabsClient = client
            customTabsClient!!.warmup(0)
            customTabsSession = customTabsClient!!.newSession(null)
        }

    }

    CustomTabsClient.bindCustomTabsService(context, packageName, customTabsServiceConnection)

    customTabsIntent = CustomTabsIntent.Builder(customTabsSession)
            .setShowTitle(true)
            .setToolbarColor(Color.BLACK)
            .setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            .build()

}
