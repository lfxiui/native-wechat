package com.hector.nativewechat;

import androidx.annotation.Nullable;

import com.facebook.react.TurboReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.module.model.ReactModuleInfoProvider;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NativeWechatPackage extends TurboReactPackage {
  @Nullable
  @Override
  public NativeModule getModule(String name, ReactApplicationContext reactContext) {
    // 添加模块名匹配的日志输出
    Log.d("WechatPackage", "Requesting module: " + name);
    
    // 修复大小写敏感问题，使用 NativeWechatModule.NAME 常量
    if (NativeWechatModule.NAME.equals(name)) {
      return new NativeWechatModule(reactContext);
    }
    return null;
  }

  // 保持原有 getReactModuleInfoProvider 不变
}
