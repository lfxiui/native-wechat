package com.hector.nativewechat

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.Arguments
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelbiz.SubscribeMessage
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.modelbiz.WXOpenCustomerServiceChat
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXImageObject
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject
import com.tencent.mm.opensdk.modelmsg.WXTextObject
import com.tencent.mm.opensdk.modelmsg.WXVideoObject
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import java.io.IOException
import java.util.HashMap
import okhttp3.Call

class NativeWechat(context: ReactApplicationContext) : IWXAPIEventHandler {
    
    companion object {
        const val NAME = "Wechat"
        private const val REDIRECT_INTENT_ACTION = "com.hector.nativewechat.ACTION_REDIRECT_INTENT"
        
        private lateinit var reactContext: ReactApplicationContext
        private var appid: String? = null
        private var registered: Boolean = false
        private lateinit var wxApi: IWXAPI
        private lateinit var instance: NativeWechat
        
        fun getConstantsStatic(): Map<String, Any> {
            val map = HashMap<String, Any>()
            
            map["WXSceneSession"] = SendMessageToWX.Req.WXSceneSession
            map["WXSceneTimeline"] = SendMessageToWX.Req.WXSceneTimeline
            map["WXSceneFavorite"] = SendMessageToWX.Req.WXSceneFavorite
            map["WXMiniProgramTypeRelease"] = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE
            map["WXMiniProgramTypeTest"] = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_TEST
            map["WXMiniProgramTypePreview"] = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_PREVIEW
            
            return map
        }
        
        @Throws(Exception::class)
        fun getApiInstance(): IWXAPI {
            if (::wxApi.isInitialized.not()) {
                throw Exception("Registration is required")
            }
            
            return wxApi
        }
        
        fun handleIntent(intent: Intent) {
            wxApi.handleIntent(intent, instance)
        }
    }
    
    fun getConstants(): Map<String, Any> {
        return getConstantsStatic()
    }
    
    init {
        reactContext = context
        instance = this
        
        if (!registered) {
            compatRegisterReceiver(
                context,
                object : BroadcastReceiver() {
                    override fun onReceive(context: Context, intent: Intent) {
                        handleIntent(intent.extras?.get("intent") as Intent)
                    }
                },
                IntentFilter(REDIRECT_INTENT_ACTION),
                true
            )
        }
    }
    
    /**
     * Starting with Android 14, apps and services that target Android 14 and use
     * context-registered receivers are required to specify a flag to indicate
     * whether or not the receiver should be exported to all other apps on the
     * device: either RECEIVER_EXPORTED or RECEIVER_NOT_EXPORTED
     *
     * @see https://developer.android.com/about/versions/14/behavior-changes-14#runtime-receivers-exported
     * @see https://github.com/react-native-share/react-native-share/issues/1463
     */
    private fun compatRegisterReceiver(
        context: Context,
        receiver: BroadcastReceiver,
        filter: IntentFilter,
        exported: Boolean
    ) {
        if (Build.VERSION.SDK_INT >= 34 &&
            context.applicationInfo.targetSdkVersion >= 34) {
            context.registerReceiver(
                receiver,
                filter,
                if (exported) Context.RECEIVER_EXPORTED else Context.RECEIVER_NOT_EXPORTED
            )
        } else {
            context.registerReceiver(receiver, filter)
        }
    }
    
    fun registerApp(request: ReadableMap) {
        appid = request.getString("appid") ?: ""
        registered = true
        
        wxApi = WXAPIFactory.createWXAPI(reactContext, appid, true)
        wxApi.registerApp(appid)
    }
    
    fun isWechatInstalled(callback: Callback) {
        callback.invoke(null, wxApi.isWXAppInstalled())
    }
    
    fun sendAuthRequest(request: ReadableMap, callback: Callback) {
        val req = SendAuth.Req()
        
        req.scope = request.getString("scope") ?: ""
        req.state = request.getString("state") ?: ""
        
        callback.invoke(if (wxApi.sendReq(req)) null else true)
    }
    
    fun shareText(request: ReadableMap, callback: Callback) {
        val text = request.getString("text") ?: ""
        val scene = request.getInt("scene")
        
        val textObj = WXTextObject()
        textObj.text = text
        
        val msg = WXMediaMessage()
        msg.mediaObject = textObj
        msg.description = text
        
        val req = SendMessageToWX.Req()
        req.message = msg
        req.scene = scene
        
        callback.invoke(if (wxApi.sendReq(req)) null else true)
    }
    
    fun shareImage(request: ReadableMap, callback: Callback) {
        val url = request.getString("src") ?: ""
        val scene = request.getInt("scene")
        
        NativeWechatUtils.loadImage(url, object : NativeWechatUtils.DownloadBitmapCallback {
            override fun onFailure(@NonNull call: Call, @NonNull e: IOException) {
                callback.invoke(true, e.message)
            }
            
            override fun onResponse(@NonNull bitmap: Bitmap) {
                val imgObj = WXImageObject(bitmap)
                
                val msg = WXMediaMessage()
                msg.mediaObject = imgObj
                
                msg.thumbData = NativeWechatUtils.bmpToByteArray(
                    NativeWechatUtils.compressImage(bitmap, 32),
                    true
                )
                bitmap.recycle()
                
                val req = SendMessageToWX.Req()
                req.message = msg
                req.scene = scene
                
                callback.invoke(if (wxApi.sendReq(req)) null else true)
            }
        })
    }
    
    fun shareVideo(request: ReadableMap, callback: Callback) {
        val videoUrl = request.getString("videoUrl") ?: ""
        val videoLowBandUrl = request.getString("videoLowBandUrl") ?: ""
        val title = request.getString("title") ?: ""
        val description = request.getString("description") ?: ""
        val coverUrl = request.getString("coverUrl") ?: ""
        val scene = request.getInt("scene")
        
        val video = WXVideoObject()
        video.videoUrl = videoUrl
        
        if (videoLowBandUrl.isNotEmpty()) {
            video.videoLowBandUrl = videoLowBandUrl
        }
        
        val msg = WXMediaMessage(video)
        msg.title = title
        msg.description = description
        
        val onCoverDownloaded = BitmapDownload { bitmap ->
            if (bitmap != null) {
                msg.thumbData = NativeWechatUtils.bmpToByteArray(
                    NativeWechatUtils.compressImage(bitmap, 32),
                    true
                )
            }
            
            val req = SendMessageToWX.Req()
            req.message = msg
            req.scene = scene
            
            callback.invoke(if (wxApi.sendReq(req)) null else true)
        }
        
        if (coverUrl.isNotEmpty()) {
            NativeWechatUtils.loadImage(coverUrl, object : NativeWechatUtils.DownloadBitmapCallback {
                override fun onFailure(@NonNull call: Call, @NonNull e: IOException) {
                    callback.invoke(true, e.message)
                }
                
                override fun onResponse(@NonNull bitmap: Bitmap) {
                    onCoverDownloaded.run(bitmap)
                }
            })
        } else {
            onCoverDownloaded.run(null)
        }
    }
    
    fun shareWebpage(request: ReadableMap, callback: Callback) {
        val webpageUrl = request.getString("webpageUrl") ?: ""
        val title = request.getString("title") ?: ""
        val description = request.getString("description") ?: ""
        val coverUrl = request.getString("coverUrl") ?: ""
        val scene = request.getInt("scene")
        
        val webpageObj = WXWebpageObject()
        webpageObj.webpageUrl = webpageUrl
        
        val msg = WXMediaMessage(webpageObj)
        msg.title = title
        msg.description = description
        
        val onCoverDownloaded = BitmapDownload { bitmap ->
            if (bitmap != null) {
                msg.thumbData = NativeWechatUtils.bmpToByteArray(
                    NativeWechatUtils.compressImage(bitmap, 32),
                    true
                )
            }
            
            val req = SendMessageToWX.Req()
            req.message = msg
            req.scene = scene
            
            callback.invoke(if (wxApi.sendReq(req)) null else true)
        }
        
        if (coverUrl.isNotEmpty()) {
            NativeWechatUtils.loadImage(coverUrl, object : NativeWechatUtils.DownloadBitmapCallback {
                override fun onFailure(@NonNull call: Call, @NonNull e: IOException) {
                    callback.invoke(true, e.message)
                }
                
                override fun onResponse(@NonNull bitmap: Bitmap) {
                    onCoverDownloaded.run(bitmap)
                }
            })
        } else {
            onCoverDownloaded.run(null)
        }
    }
    
    fun shareMiniProgram(request: ReadableMap, callback: Callback) {
        val webpageUrl = request.getString("webpageUrl") ?: ""
        val userName = request.getString("userName") ?: ""
        val path = request.getString("path") ?: ""
        val title = request.getString("title") ?: ""
        val description = request.getString("description") ?: ""
        val coverUrl = request.getString("coverUrl") ?: ""
        val withShareTicket = request.getBoolean("withShareTicket")
        val miniProgramType = request.getInt("miniProgramType")
        val scene = request.getInt("scene")
        
        val miniProgramObj = WXMiniProgramObject()
        miniProgramObj.webpageUrl = webpageUrl
        miniProgramObj.miniprogramType = miniProgramType
        miniProgramObj.userName = userName
        miniProgramObj.path = path
        miniProgramObj.withShareTicket = withShareTicket
        
        val msg = WXMediaMessage(miniProgramObj)
        msg.title = title
        msg.description = description
        
        val onCoverDownloaded = BitmapDownload { bitmap ->
            if (bitmap != null) {
                msg.thumbData = NativeWechatUtils.bmpToByteArray(
                    NativeWechatUtils.compressImage(bitmap, 32),
                    true
                )
            }
            
            val req = SendMessageToWX.Req()
            req.message = msg
            req.scene = scene
            
            callback.invoke(if (wxApi.sendReq(req)) null else true)
        }
        
        if (coverUrl.isNotEmpty()) {
            NativeWechatUtils.loadImage(coverUrl, object : NativeWechatUtils.DownloadBitmapCallback {
                override fun onFailure(@NonNull call: Call, @NonNull e: IOException) {
                    callback.invoke(true, e.message)
                }
                
                override fun onResponse(@NonNull bitmap: Bitmap) {
                    onCoverDownloaded.run(bitmap)
                }
            })
        } else {
            onCoverDownloaded.run(null)
        }
    }
    
    fun requestPayment(request: ReadableMap, callback: Callback) {
        val payReq = PayReq()
        
        payReq.partnerId = request.getString("partnerId") ?: ""
        payReq.prepayId = request.getString("prepayId") ?: ""
        payReq.nonceStr = request.getString("nonceStr") ?: ""
        payReq.timeStamp = request.getString("timeStamp") ?: ""
        payReq.sign = request.getString("sign") ?: ""
        payReq.packageValue = "Sign=WXPay"
        payReq.extData = request.getString("extData") ?: ""
        payReq.appId = appid ?: ""
        
        callback.invoke(if (wxApi.sendReq(payReq)) null else true)
    }
    
    fun requestSubscribeMessage(request: ReadableMap, callback: Callback) {
        val templateId = request.getString("templateId") ?: ""
        val reserved = request.getString("reserved") ?: ""
        val scene = request.getInt("int")
        
        val req = SubscribeMessage.Req()
        req.scene = scene
        req.templateID = templateId
        req.reserved = reserved
        
        callback.invoke(if (wxApi.sendReq(req)) null else true)
    }
    
    fun launchMiniProgram(request: ReadableMap, callback: Callback) {
        val userName = request.getString("userName") ?: ""
        val path = request.getString("path") ?: ""
        val miniProgramType = request.getInt("miniProgramType")
        
        val req = WXLaunchMiniProgram.Req()
        req.userName = userName
        req.path = path
        req.miniprogramType = miniProgramType
        
        callback.invoke(if (wxApi.sendReq(req)) null else true)
    }
    
    fun openCustomerService(request: ReadableMap, callback: Callback) {
        val corpId = request.getString("corpid") ?: ""
        val url = request.getString("url") ?: ""
        
        val req = WXOpenCustomerServiceChat.Req()
        req.corpId = corpId
        req.url = url
        
        callback.invoke(if (wxApi.sendReq(req)) null else true)
    }
    
    override fun onReq(req: BaseReq) {
        // 实现为空
    }
    
    override fun onResp(baseResp: BaseResp) {
        val convertedData = NativeWechatRespDataHelper.downcastResp(baseResp)
        
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
            .emit("NativeWechat_Response", convertedData)
    }
    
    fun interface BitmapDownload {
        fun run(@Nullable bitmap: Bitmap?)
    }
    
    fun getName(): String {
        return NAME
    }
} 