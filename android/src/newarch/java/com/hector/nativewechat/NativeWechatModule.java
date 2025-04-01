package com.hector.nativewechat;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.turbomodule.core.interfaces.TurboModule;

import java.util.Map;

@ReactModule(name = NativeWechatModule.NAME)
public class NativeWechatModule extends ReactContextBaseJavaModule implements TurboModule {
  public static final String NAME = "Wechat";
  private NativeWechatModuleImpl moduleImpl;

  NativeWechatModule(ReactApplicationContext context) {
    super(context);
    moduleImpl = new NativeWechatModuleImpl(context);
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public Map<String, Object> getConstants() {
    return NativeWechatModuleImpl.getConstants();
  }

  @ReactMethod
  public void sendAuthRequest(ReadableMap request, Callback callback) {
    moduleImpl.sendAuthRequest(request, callback);
  }

  @ReactMethod
  public void registerApp(ReadableMap request) {
    moduleImpl.registerApp(request);
  }

  @ReactMethod
  public void shareText(ReadableMap request, Callback callback) {
    moduleImpl.shareText(request, callback);
  }

  @ReactMethod
  public void shareImage(ReadableMap request, Callback callback) {
    moduleImpl.shareImage(request, callback);
  }

  @ReactMethod
  public void shareVideo(ReadableMap request, Callback callback) {
    moduleImpl.shareVideo(request, callback);
  }

  @ReactMethod
  public void shareWebpage(ReadableMap request, Callback callback) {
    moduleImpl.shareWebpage(request, callback);
  }

  @ReactMethod
  public void shareMiniProgram(ReadableMap request, Callback callback) {
    moduleImpl.shareMiniProgram(request, callback);
  }

  @ReactMethod
  public void isWechatInstalled(Callback callback) {
    moduleImpl.isWechatInstalled(callback);
  }

  @ReactMethod
  public void requestPayment(ReadableMap request, Callback callback) {
    moduleImpl.requestPayment(request, callback);
  }

  @ReactMethod
  public void requestSubscribeMessage(ReadableMap request, Callback callback) {
    moduleImpl.requestSubscribeMessage(request, callback);
  }

  @ReactMethod
  public void launchMiniProgram(ReadableMap request, Callback callback) {
    moduleImpl.launchMiniProgram(request, callback);
  }

  @ReactMethod
  public void openCustomerService(ReadableMap request, Callback callback) {
    moduleImpl.openCustomerService(request, callback);
  }

  @ReactMethod
  public void addListener(String eventName) {
    // 保持空实现，只是为了满足接口要求
  }
  
  @ReactMethod
  public void removeListeners(double count) {
    // 保持空实现，只是为了满足接口要求
  }
}
