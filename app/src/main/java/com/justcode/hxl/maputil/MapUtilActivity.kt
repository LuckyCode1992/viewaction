package com.justcode.hxl.maputil

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.justcode.hxl.permission.RxPermissions
import com.justcode.hxl.tools.maputil.LngLat
import com.justcode.hxl.tools.maputil.MapUtil
import com.justcode.hxl.tools.maputil.TransUtil
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_map_util.*

class MapUtilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_util)

        btn_gaode.setOnClickListener {
            RxPermissions(this).request(Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe {
                        if (it) {
                            if (MapUtil.getIntance(this).hasGaodeMap()){
                                var baidu2GaodeorTentxun = TransUtil.baidu2GaodeorTentxun(LngLat(106.608907, 29.614212))
                                MapUtil.getIntance(this).myLocation2WhereByGaode(baidu2GaodeorTentxun.lantitude.toFloat(), baidu2GaodeorTentxun.longitude.toFloat(), "我的目的地")
                            }


                        } else {
                            Toast.makeText(this, "请给定位权限", Toast.LENGTH_SHORT).show()
                        }
                    }
        }
        btn_baidu.setOnClickListener {
            RxPermissions(this).request(Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe {
                        if (it) {
                            if (MapUtil.getIntance(this).hasBaiduMap())
                                MapUtil.getIntance(this).myLocation2WhereByBaidu(29.614212f, 106.608907f)
                        } else {
                            Toast.makeText(this, "请给定位权限", Toast.LENGTH_SHORT).show()
                        }
                    }
        }
        btn_tenxung.setOnClickListener {
            RxPermissions(this).request(Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe {
                        if (it) {
                            if (MapUtil.getIntance(this).hasBaiduMap())
                                MapUtil.getIntance(this).myLocation2WhereByTengxun(29.614212f, 106.608907f)
                        } else {
                            Toast.makeText(this, "请给定位权限", Toast.LENGTH_SHORT).show()
                        }
                    }
        }


    }
}
