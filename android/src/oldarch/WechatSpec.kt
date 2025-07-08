package com.hector.nativewechat

import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableMap

abstract class WechatSpec internal constructor(context: ReactApplicationContext) : ReactContextBaseJavaModule(context) {

    abstract fun getConstants(): Map<String, Any>?

    abstract fun sendAuthRequest(request: ReadableMap, callback: Callback) {
    }

    abstract  fun registerApp(request: ReadableMap) {
    }

    abstract  fun shareText(request: ReadableMap, callback: Callback) {
    }

    abstract  fun shareImage(request: ReadableMap, callback: Callback) {
    }

    abstract  fun shareVideo(request: ReadableMap, callback: Callback) {
    }

    abstract  fun shareWebpage(request: ReadableMap, callback: Callback) {
    }

    abstract  fun shareMiniProgram(request: ReadableMap, callback: Callback) {
    }

    abstract  fun isWechatInstalled(callback: Callback) {
    }

    abstract  fun requestPayment(request: ReadableMap, callback: Callback) {
    }

    abstract  fun requestSubscribeMessage(request: ReadableMap, callback: Callback) {
    }

    abstract  fun launchMiniProgram(request: ReadableMap, callback: Callback) {
    }

    abstract  fun openCustomerService(request: ReadableMap, callback: Callback) {
    }

    abstract fun addListener(eventType: String)

    abstract fun removeListeners(count: Double)
}