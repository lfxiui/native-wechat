cmake_minimum_required(VERSION 3.13)
project("native-wechat")

include(${REACT_ANDROID_DIR}/cmake-utils/ReactNative-application.cmake)

# 添加微信 SDK 的 JNI 目录
add_subdirectory(${CMAKE_CURRENT_SOURCE_DIR}/src/main/jni) 