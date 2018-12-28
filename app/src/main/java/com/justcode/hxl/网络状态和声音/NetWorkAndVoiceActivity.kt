package com.justcode.hxl.网络状态和声音

import android.media.AudioManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.tools.NetWork.NetWorkUtil
import com.justcode.hxl.tools.voice.VoiceUtil
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_net_work_and_voice.*

class NetWorkAndVoiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_work_and_voice)
        var voiceUtil = VoiceUtil.getInstance().init(this)

        btn1.setOnClickListener {
            NetWorkUtil.getNetWorkSignal(this, {
                tv1.text = "网络强度：" + it
            })
        }
        btn2.setOnClickListener {
            tv2.text =  "多媒体音量："+voiceUtil.getCurrentVolume(VoiceUtil.AudioMangerTag.VOICE_MEDIA)
        }
    }
}
