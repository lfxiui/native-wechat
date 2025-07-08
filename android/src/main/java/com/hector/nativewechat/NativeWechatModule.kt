package com.hector.nativewechat

import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.WritableMap

class NativeWechatModule internal constructor(context: ReactApplicationContext) : ReactContextBaseJavaModule(context) {
    private val moduleImpl = NativeWechat(context)

    override fun getName(): String = NAME

    override fun getConstants(): Map<String, Any>? = moduleImpl.getConstants()

    @ReactMethod
    fun sendAuthRequest(request: ReadableMap, callback: Callback) {
        moduleImpl.sendAuthRequest(request, callback)
    }

    @ReactMethod
    fun registerApp(request: ReadableMap) {
        moduleImpl.registerApp(request)
    }

    @ReactMethod
    fun shareText(request: ReadableMap, callback: Callback) {
        moduleImpl.shareText(request, callback)
    }

    @ReactMethod
    fun shareImage(request: ReadableMap, callback: Callback) {
        moduleImpl.shareImage(request, callback)
    }

    @ReactMethod
    fun shareVideo(request: ReadableMap, callback: Callback) {
        moduleImpl.shareVideo(request, callback)
    }

    @ReactMethod
    fun shareWebpage(request: ReadableMap, callback: Callback) {
        moduleImpl.shareWebpage(request, callback)
    }

    @ReactMethod
    fun shareMiniProgram(request: ReadableMap, callback: Callback) {
        moduleImpl.shareMiniProgram(request, callback)
    }

    @ReactMethod
    fun isWechatInstalled(callback: Callback) {
        moduleImpl.isWechatInstalled(callback)
    }

    @ReactMethod
    fun requestPayment(request: ReadableMap, callback: Callback) {
        moduleImpl.requestPayment(request, callback)
    }

    @ReactMethod
    fun requestSubscribeMessage(request: ReadableMap, callback: Callback) {
        moduleImpl.requestSubscribeMessage(request, callback)
    }

    @ReactMethod
    fun launchMiniProgram(request: ReadableMap, callback: Callback) {
        moduleImpl.launchMiniProgram(request, callback)
    }

    @ReactMethod
    fun openCustomerService(request: ReadableMap, callback: Callback) {
        moduleImpl.openCustomerService(request, callback)
    }

    fun addListener(eventType: String) {

    }

    fun removeListeners(count: Double) {

    }

    companion object {
        const val NAME = "Wechat"
    }
}