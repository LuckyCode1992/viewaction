package com.justcode.hxl.进度条

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_progross.*

class ProgressActivity : AppCompatActivity() {
    var money = 1000.0
    var downLoadThread: Thread? = null
    var downLoadThread2: Thread? = null
    var downLoadRxRoundPdThread: Thread? = null
    var handler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            loading.progress = msg.arg1.toFloat()
            if (msg.arg1 == 100) {
                loading.finishLoad()
            }
        }
    }

    private var progress: Double = 0.toDouble()

    private var mRxRoundProgress: Float = 0f

    var mRxRoundPdHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            roundbar.progress = mRxRoundProgress.toFloat()
            iconround.progress = mRxRoundProgress.toFloat()
            textround.progress = mRxRoundProgress.toFloat()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progross)

        initRoundProgress()
        initRxRoundPd()

        downLoad()

        btn.setOnClickListener {
            downLoadThread?.interrupt()
            // 重新加载
            loading.reset()

            downLoad()
        }
    }

    private fun downLoad() {
        downLoadThread = Thread(Runnable {
            try {
                while (!downLoadThread?.isInterrupted()!!) {
                    var progress = loading.getProgress()
                    progress += 2f
                    Thread.sleep(200)
                    val message = handler.obtainMessage()
                    message.arg1 = progress.toInt()
                    handler.sendMessage(message)
                    if (progress.toFloat() == 100f) {
                        break
                    }
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        })
        downLoadThread?.start()
    }

    private fun initRoundProgress() {
        // TODO Auto-generated method stub
        progress = 0.0// 进度初始化

        round.setProgress(progress.toDouble())
        round.setMax(getMax(money).toDouble())

        downLoadThread2 = Thread(Runnable {
            try {
                while (!downLoadThread2?.isInterrupted()!!) {
                    while (progress < round.getMax()) {
                        progress += round.getMax() * 0.01
                        if (progress < round.getMax()) {
                            round.setProgress(progress.toDouble())
                        }
                        Thread.sleep(8)
                    }
                    while (progress > 0) {
                        progress -= round.getMax() * 0.01
                        if (progress > 0) {
                            round.setProgress(progress.toDouble())
                        }
                        Thread.sleep(8)
                    }

                    if (money != 0.0) {
                        while (progress < money) {
                            progress += money * 0.01
                            round.setProgress(progress.toDouble())
                            Thread.sleep(10)
                        }
                    }

                    round.setProgress(money.toDouble())
                }
            } catch (e: InterruptedException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
        })
        downLoadThread2?.start()
    }

    fun getMax(currentProgress: Double): Int {
        return if (currentProgress < 100 && currentProgress > 0) {
            100
        } else if (currentProgress >= 100 && currentProgress < 1000) {
            1000
        } else if (currentProgress >= 1000 && currentProgress < 5000) {
            5000
        } else if (currentProgress >= 5000 && currentProgress < 20000) {
            20000
        } else if (currentProgress >= 20000 && currentProgress < 100000) {
            100000
        } else if (currentProgress >= 100000) {
            stringToInt((currentProgress * 1.1).toString() + "")
        } else {
            stringToInt(currentProgress.toString() + "")
        }
    }

    /**
     * 字符串转换成整数 ,转换失败将会 return 0;
     *
     * @param str 字符串
     * @return
     */
    fun stringToInt(str: String): Int {
        return if (TextUtils.isEmpty(str)) {
            0
        } else {
            try {
                Integer.parseInt(str)
            } catch (e: NumberFormatException) {
                0
            }

        }
    }

    var mRxRoundPdMax = 100
    private fun initRxRoundPd() {
        // TODO Auto-generated method stub
        mRxRoundProgress = 0f// 进度初始化

        roundbar.setMax(mRxRoundPdMax.toFloat())
        iconround.max = mRxRoundPdMax.toFloat()
        textround.max = mRxRoundPdMax.toFloat()
        downLoadRxRoundPdThread = Thread(Runnable {
            try {
                while (!downLoadRxRoundPdThread?.isInterrupted()!!) {
                    while (mRxRoundProgress < roundbar.getMax()) {
                        mRxRoundProgress = (mRxRoundProgress + roundbar.getMax() * 0.01).toFloat()
                        if (mRxRoundProgress < roundbar.getMax()) {
                            val message = Message()
                            message.what = 101
                            mRxRoundPdHandler.sendMessage(message)
                        }
                        Thread.sleep(8)
                    }
                    while (mRxRoundProgress > 0) {
                        mRxRoundProgress = (mRxRoundProgress - roundbar.getMax() * 0.01).toFloat()
                        if (mRxRoundProgress > 0) {
                            val message = Message()
                            message.what = 101
                            mRxRoundPdHandler.sendMessage(message)
                        }
                        Thread.sleep(8)
                    }

                    while (mRxRoundProgress < mRxRoundPdMax) {
                        mRxRoundProgress += (mRxRoundPdMax * 0.01).toInt()
                        val message = Message()
                        message.what = 101
                        mRxRoundPdHandler.sendMessage(message)
                        Thread.sleep(10)
                    }
                }
            } catch (e: InterruptedException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
        })
        downLoadRxRoundPdThread?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        downLoadThread?.interrupt()

        downLoadThread2?.interrupt()
        downLoadRxRoundPdThread?.interrupt()
    }
}
