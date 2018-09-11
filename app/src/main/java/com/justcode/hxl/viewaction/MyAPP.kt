package com.justcode.hxl.viewaction

import android.annotation.TargetApi
import android.app.Application
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import android.support.annotation.RequiresApi
import com.justcode.hxl.viewaction.gesture.GestureActivity
import com.justcode.hxl.viewaction.shuxingdonghua.Main2Activity
import com.justcode.hxl.viewaction.view_touch.OntouchActivity
import java.util.*

class MyAPP : Application() {
    companion object {
        var myApp: MyAPP? = null
    }

    var list: MutableList<ShortcutInfo> = ArrayList()
    var shortcutManager: ShortcutManager? = null
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate() {
        super.onCreate()
        myApp = this
        shortcutManager = getSystemService(ShortcutManager::class.java)

        setShortcutInfo()
    }

    @TargetApi(Build.VERSION_CODES.N_MR1)
    fun setShortcutInfo() {
        for (i in 1..4) {
            var intent = Intent()
            intent.setAction(Intent.ACTION_VIEW);
            when (i) {
                1 -> {
                    intent.setClass(this, MainActivity::class.java)
                }
                2 -> {
                    intent.setClass(this, Main2Activity::class.java)
                }
                3 -> {
                    intent.setClass(this, GestureActivity::class.java)
                }
                4 -> {
                    intent.setClass(this, OntouchActivity::class.java)
                }
            }
            var shortcutInfo = ShortcutInfo.Builder(this,"id"+i)
                    .setShortLabel("跳转到  "+i)
                    .setLongLabel("跳转到activity  "+i)
                    .setIcon(Icon.createWithResource(this,R.drawable.kefu_2_pdf))
                    .setIntent(intent)
                    .build()
            list.add(shortcutInfo)
            //shortcutManager?.addDynamicShortcuts(Arrays.asList(shortcutInfo))
        }
        shortcutManager?.addDynamicShortcuts(list)

    }
}