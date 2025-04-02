package com.hector.nativewechat;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = NativeWechatModule.NAME)
public class NativeWechatModule extends NativeWechatSpec {
  public static final String NAME = "Wechat";
  private NativeWechatModuleImpl moduleImpl;

  public NativeWechatModule(ReactApplicationContext context) {
    super(context);
    moduleImpl = new NativeWechatModuleImpl(context);
  }

  @Override
  public void sendAuthRequest(ReadableMap request, Callback callback) {
    moduleImpl.sendAuthRequest(request, callback);
  }

  @Override
  public void registerApp(ReadableMap request) {
    moduleImpl.registerApp(request);
  }

  @Override
  public void shareText(ReadableMap request, Callback callback) {
    moduleImpl.shareText(request, callback);
  }

  @Override
  public void shareImage(ReadableMap request, Callback callback) {
    moduleImpl.shareImage(request, callback);
  }

  @Override
  public void shareVideo(ReadableMap request, Callback callback) {
    moduleImpl.shareVideo(request, callback);
  }

  @Override
  public void shareWebpage(ReadableMap request, Callback callback) {
    moduleImpl.shareWebpage(request, callback);
  }

  @Override
  public void shareMiniProgram(ReadableMap request, Callback callback) {
    moduleImpl.shareMiniProgram(request, callback);
  }

  @Override
  public void isWechatInstalled(Callback callback) {
    moduleImpl.isWechatInstalled(callback);
  }

  @Override
  public void requestPayment(ReadableMap request, Callback callback) {
    moduleImpl.requestPayment(request, callback);
  }

  @Override
  public void requestSubscribeMessage(ReadableMap request, Callback callback) {
    moduleImpl.requestSubscribeMessage(request, callback);
  }

  @Override
  public void launchMiniProgram(ReadableMap request, Callback callback) {
    moduleImpl.launchMiniProgram(request, callback);
  }

  @Override
  public void openCustomerService(ReadableMap request, Callback callback) {
    moduleImpl.openCustomerService(request, callback);
  }

  @Override
  public void addListener(String eventName) {
    // 保持空实现，只是为了满足接口要求
  }
  
  @Override
  public void removeListeners(double count) {
    // 保持空实现，只是为了满足接口要求
  }
}
