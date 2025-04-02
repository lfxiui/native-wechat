package com.hector.nativewechat

import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReadableMap

abstract class WechatSpec internal constructor(context: ReactApplicationContext) : ReactContextBaseJavaModule(context) {
    abstract  fun getName(): String = NAME

    abstract  fun getConstants(): Map<String, Any>? = moduleImpl.constants

    abstract fun sendAuthRequest(request: ReadableMap, callback: Callback) {
        moduleImpl.sendAuthRequest(request, callback)
    }

    abstract  fun registerApp(request: ReadableMap) {
        moduleImpl.registerApp(request)
    }

    abstract  fun shareText(request: ReadableMap, callback: Callback) {
        moduleImpl.shareText(request, callback)
    }

    abstract  fun shareImage(request: ReadableMap, callback: Callback) {
        moduleImpl.shareImage(request, callback)
    }

    abstract  fun shareVideo(request: ReadableMap, callback: Callback) {
        moduleImpl.shareVideo(request, callback)
    }

    abstract  fun shareWebpage(request: ReadableMap, callback: Callback) {
        moduleImpl.shareWebpage(request, callback)
    }

    abstract  fun shareMiniProgram(request: ReadableMap, callback: Callback) {
        moduleImpl.shareMiniProgram(request, callback)
    }

    abstract  fun isWechatInstalled(callback: Callback) {
        moduleImpl.isWechatInstalled(callback)
    }

    abstract  fun requestPayment(request: ReadableMap, callback: Callback) {
        moduleImpl.requestPayment(request, callback)
    }

    abstract  fun requestSubscribeMessage(request: ReadableMap, callback: Callback) {
        moduleImpl.requestSubscribeMessage(request, callback)
    }

    abstract  fun launchMiniProgram(request: ReadableMap, callback: Callback) {
        moduleImpl.launchMiniProgram(request, callback)
    }

    abstract  fun openCustomerService(request: ReadableMap, callback: Callback) {
        moduleImpl.openCustomerService(request, callback)
    }
}