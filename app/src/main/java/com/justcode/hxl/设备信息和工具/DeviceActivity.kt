package com.justcode.hxl.设备信息和工具

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.justcode.hxl.tools.设备信息.DeviceTool
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_device.*

class DeviceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)

        tv_屏幕的高.text = DeviceTool.getScreenHeight(this).toString()
        tv_屏幕的宽.text = DeviceTool.getScreenWidth(this).toString()
        tv_设备屏幕的宽度.text = DeviceTool.getScreenWidths(this).toString()
        tv_设备屏幕的高度.text = DeviceTool.getScreenHeights(this).toString()
        tv_设备的密度.text = DeviceTool.getScreenDensity(this).toString()
        tv_UniqueSerialNumber.text = DeviceTool.getUniqueSerialNumber()
        tv_IMEI.text = DeviceTool.getIMEI(this)
        tv_IMSI.text = DeviceTool.getIMSI(this)
        tv_DeviceIdIMEI.text = DeviceTool.getDeviceIdIMEI(this)
        tv_DeviceSoftwareVersion.text = DeviceTool.getDeviceSoftwareVersion(this)
        tv_Line1Number.text = DeviceTool.getLine1Number(this)
        tv_NetworkCountryIso.text = DeviceTool.getNetworkCountryIso(this)
        tv_NetworkOperator.text = DeviceTool.getNetworkOperator(this)
        tv_NetworkOperatorName.text = DeviceTool.getNetworkOperatorName(this)
        tv_NetworkType.text = DeviceTool.getNetworkType(this).toString()
        tv_PhoneType.text =DeviceTool.getPhoneType(this).toString()
        tv_SimCountryIso.text = DeviceTool.getSimCountryIso(this)
        tv_SimOperator.text = DeviceTool.getSimOperator(this)
        tv_SimSerialNumber.text = DeviceTool.getSimSerialNumber(this)
        tv_SimState.text = DeviceTool.getSimState(this).toString()
        tv_SubscriberId.text = DeviceTool.getSubscriberId(this)
        tv_AndroidId.text = DeviceTool.getAndroidId(this)
        tv_BuildBrandModel.text = DeviceTool.getBuildBrandModel()
        tv_BuildBrand.text = DeviceTool.getBuildBrand()
        tv_BuildMANUFACTURER.text = DeviceTool.getBuildMANUFACTURER()
        tv_SerialNumber.text = DeviceTool.getSerialNumber()
        tv_AppPackageName.text = DeviceTool.getAppPackageName(this)
        tv_ppVersionName.text = DeviceTool.getAppVersionName(this)
        tv_DeviceInfo.text = DeviceTool.getDeviceInfo(this)
        tv_MacAddress.text = DeviceTool.getMacAddress()
        tv_StatusBarHeight.text = DeviceTool.getStatusBarHeight(this).toString()
        tv_wifiname.text = DeviceTool.getWifiSSID(this)
        tv_wifiMac.text = DeviceTool.getWifiMacAdress(this)

        tv_dial.setOnClickListener {
            var toString = et_phone.text.toString()
            if (TextUtils.isEmpty(toString)) {
                return@setOnClickListener
            }
            DeviceTool.dial(this, toString)
        }
        tv_callPhone.setOnClickListener {
            var toString = et_phone2.text.toString()
            if (TextUtils.isEmpty(toString)) {
                return@setOnClickListener
            }
            DeviceTool.callPhone(this, toString)
        }

    }
}
