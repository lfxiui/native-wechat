package com.hector.nativewechat

import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.ReactMethod

abstract class NativeWechatModule internal constructor(context: ReactApplicationContext) : WechatSpec(context) {
    private val moduleImpl = NativeWechatModuleImpl(context)

    override fun getName(): String = NAME

    @ReactMethod
    override fun getConstants(): Map<String, Any>? = moduleImpl.getConstants()

    @ReactMethod
    override fun sendAuthRequest(request: ReadableMap, callback: Callback) {
        moduleImpl.sendAuthRequest(request, callback)
    }

    @ReactMethod
    override fun registerApp(request: ReadableMap) {
        moduleImpl.registerApp(request)
    }

    @ReactMethod
    override fun shareText(request: ReadableMap, callback: Callback) {
        moduleImpl.shareText(request, callback)
    }

    @ReactMethod
    override fun shareImage(request: ReadableMap, callback: Callback) {
        moduleImpl.shareImage(request, callback)
    }

    @ReactMethod
    override fun shareVideo(request: ReadableMap, callback: Callback) {
        moduleImpl.shareVideo(request, callback)
    }

    @ReactMethod
    override fun shareWebpage(request: ReadableMap, callback: Callback) {
        moduleImpl.shareWebpage(request, callback)
    }

    @ReactMethod
    override fun shareMiniProgram(request: ReadableMap, callback: Callback) {
        moduleImpl.shareMiniProgram(request, callback)
    }

    @ReactMethod
    override fun isWechatInstalled(callback: Callback) {
        moduleImpl.isWechatInstalled(callback)
    }

    @ReactMethod
    override fun requestPayment(request: ReadableMap, callback: Callback) {
        moduleImpl.requestPayment(request, callback)
    }

    @ReactMethod
    override fun requestSubscribeMessage(request: ReadableMap, callback: Callback) {
        moduleImpl.requestSubscribeMessage(request, callback)
    }

    @ReactMethod
    override fun launchMiniProgram(request: ReadableMap, callback: Callback) {
        moduleImpl.launchMiniProgram(request, callback)
    }

    @ReactMethod
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