package com.justcode.hxl.权限问题

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.CompoundButton
import android.widget.Toast
import com.justcode.hxl.permission.RxPermissions
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_permissions.*

class PermissionsActivity : AppCompatActivity() {
    var set: MutableSet<String> = HashSet<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissions)
        var listener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                set.add("android.permission." + buttonView.tag.toString())
            } else {
                set.remove("android.permission." + buttonView.tag.toString())
            }
        }
        cb_READ_CALENDAR.setOnCheckedChangeListener(listener)
        cb_ACCESS_FINE_LOCATION.setOnCheckedChangeListener(listener)
        cb_BODY_SENSORS.setOnCheckedChangeListener(listener)
        cb_CALL_PHONE.setOnCheckedChangeListener(listener)
        cb_CAMERA.setOnCheckedChangeListener(listener)
        cb_RECORD_AUDIO.setOnCheckedChangeListener(listener)
        cb_SEND_SMS.setOnCheckedChangeListener(listener)
        cb_WRITE_CONTACTS.setOnCheckedChangeListener(listener)
        cb_WRITE_EXTERNAL_STORAGE.setOnCheckedChangeListener(listener)


        btn.setOnClickListener {
            var list: MutableList<String> = ArrayList()
            set.forEach {
                list.add(it)
            }
            if (list.size == 0) {
                Toast.makeText(this, "请先选择至少一条权限", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            RxPermissions(this).request(list)
                    .subscribe {
                        if (it) {
                            Toast.makeText(this, "权限全部获取成功", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "没有权限无法进入", Toast.LENGTH_SHORT).show()
                        }
                    }
        }

        btn_cancale.setOnClickListener {
            val intent = Intent()
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:$packageName"))
            startActivityForResult(intent, 100)
        }


        RxPermissions(this).request(Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO)
                .subscribe {
                    if (it) {

                    } else {
                        Toast.makeText(this, "没有权限无法进入", Toast.LENGTH_SHORT).show()
                    }
                }
    }
}
