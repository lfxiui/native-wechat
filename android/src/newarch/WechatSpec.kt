package com.hector.nativewechat

import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableMap
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram

abstract class WechatSpec internal constructor(context: ReactApplicationContext) : NativeWechatSpec(context) {
    private val moduleImpl = NativeWechat(context)

    override fun getName(): String = NAME

    // 在新架构中，getConstants 是 final 方法，我们需要实现 getTypedExportedConstants
    override fun getTypedExportedConstants(): MutableMap<String, Any> {
        val constants = mutableMapOf<String, Any>()
        constants["WXSceneSession"] = SendMessageToWX.Req.WXSceneSession
        constants["WXSceneTimeline"] = SendMessageToWX.Req.WXSceneTimeline
        constants["WXSceneFavorite"] = SendMessageToWX.Req.WXSceneFavorite
        constants["WXMiniProgramTypeRelease"] = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE
        constants["WXMiniProgramTypeTest"] = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_TEST
        constants["WXMiniProgramTypePreview"] = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_PREVIEW
        return constants
    }

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