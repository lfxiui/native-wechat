cmake_minimum_required(VERSION 3.13)
project("native-wechat-jni")

# 添加微信 SDK 的 JNI 源文件
add_library(native-wechat-jni SHARED
  NativeWechatModule.cpp
  NativeWechatPackage.cpp
)

target_link_libraries(native-wechat-jni
  jsi
  reactnativejni
  wechat-sdk-android
) 