package com.hector.nativewechat

import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableMap

abstract class WechatSpec internal constructor(context: ReactApplicationContext) : NativeWechatSpec(context) {
    private val moduleImpl = NativeWechat(context)

    override fun getName(): String = NAME

    override fun getConstants(): Map<String, Any>? = moduleImpl.getConstants()

    override fun sendAuthRequest(request: ReadableMap, callback: Callback) {
        moduleImpl.sendAuthRequest(request, callback)
    }

    override fun registerApp(request: ReadableMap) {
        moduleImpl.registerApp(request)
    }

    override fun shareText(request: ReadableMap, callback: Callback) {
        moduleImpl.shareText(request, callback)
    }

    override fun shareImage(request: ReadableMap, callback: Callback) {
        moduleImpl.shareImage(request, callback)
    }

    override fun shareVideo(request: ReadableMap, callback: Callback) {
        moduleImpl.shareVideo(request, callback)
    }

    override fun shareWebpage(request: ReadableMap, callback: Callback) {
        moduleImpl.shareWebpage(request, callback)
    }

    override fun shareMiniProgram(request: ReadableMap, callback: Callback) {
        moduleImpl.shareMiniProgram(request, callback)
    }

    override fun isWechatInstalled(callback: Callback) {
        moduleImpl.isWechatInstalled(callback)
    }

    override fun requestPayment(request: ReadableMap, callback: Callback) {
        moduleImpl.requestPayment(request, callback)
    }

    override fun requestSubscribeMessage(request: ReadableMap, callback: Callback) {
        moduleImpl.requestSubscribeMessage(request, callback)
    }

    override fun launchMiniProgram(request: ReadableMap, callback: Callback) {
        moduleImpl.launchMiniProgram(request, callback)
    }

    override fun openCustomerService(request: ReadableMap, callback: Callback) {
        moduleImpl.openCustomerService(request, callback)
    }

    override fun addListener(eventType: String) {

    }

    override fun removeListeners(count: Double) {

    }

    companion object {
        const val NAME = "Wechat"
    }
}