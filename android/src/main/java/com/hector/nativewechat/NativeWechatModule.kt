package com.hector.nativewechat

import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.WritableMap

class NativeWechatModule internal constructor(context: ReactApplicationContext) : WechatSpec(context) {

    override fun getName(): String = NAME

    companion object {
        const val NAME = "Wechat"
    }
}