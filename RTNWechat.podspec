require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

folly_compiler_flags = '-DFOLLY_NO_CONFIG -DFOLLY_MOBILE=1 -DFOLLY_USE_LIBCPP=1 -Wno-comma -Wno-shorten-64-to-32 -DFOLLY_HAS_COROUTINES=0 -DFOLLY_NO_COROUTINES=1 -DFOLLY_NO_EXCEPTION=1 -DFOLLY_NO_RTTI=1 -DFOLLY_MOBILE=1 -DFOLLY_USE_LIBCPP=1 -DFOLLY_HAS_COROUTINES=0 -DFOLLY_NO_COROUTINES=1'

Pod::Spec.new do |s|
  s.name            = "RTNWechat"
  s.version         = package["version"]
  s.summary         = package["description"]
  s.description     = package["description"]
  s.homepage        = package["homepage"]
  s.license         = package["license"]
  s.platforms       = { :ios => "11.0" }
  s.author          = package["author"]
  s.source          = { :git => package["repository"], :tag => "#{s.version}" }

  s.source_files    = "ios/**/*.{h,m,mm,swift}"
  s.dependency "React-Core"
  
  # 设置预处理器宏以便在代码中检测模拟器
  s.pod_target_xcconfig = { 
    "GCC_PREPROCESSOR_DEFINITIONS" => "$(inherited) TARGET_IPHONE_SIMULATOR=1"
  }
  
  # 创建真机和模拟器子模块
  s.subspec 'Device' do |device|
    device.source_files = "ios/**/*.{h,m,mm,swift}"
    device.dependency "WechatOpenSDK"
    device.pod_target_xcconfig = { "GCC_PREPROCESSOR_DEFINITIONS" => "$(inherited)" }
    device.xcconfig = { "VALID_ARCHS" => "arm64 armv7" }
  end
  
  s.subspec 'Simulator' do |sim|
    sim.source_files = "ios/**/*.{h,m,mm,swift}" 
    sim.pod_target_xcconfig = { "GCC_PREPROCESSOR_DEFINITIONS" => "$(inherited) TARGET_IPHONE_SIMULATOR=1" }
    sim.xcconfig = { "VALID_ARCHS" => "x86_64 i386 arm64" }
  end
  
  # 根据平台选择子模块
  s.user_target_xcconfig = {}
  s.default_subspecs = 'Simulator'
  if ENV['PLATFORM_NAME'].to_s.include?('iphone') && !ENV['PLATFORM_NAME'].to_s.include?('simulator')
    s.default_subspecs = 'Device'
  end
  
  if ENV['RCT_NEW_ARCH_ENABLED'] == '1' then
    s.compiler_flags = folly_compiler_flags + " -DRCT_NEW_ARCH_ENABLED=1"
    
    # React New Architecture 相关设置
    new_arch_xcconfig = {
      "HEADER_SEARCH_PATHS" => "\"$(PODS_ROOT)/boost\" \"$(PODS_ROOT)/Headers/Public/ReactCommon\" \"$(PODS_ROOT)/Headers/Public/React\" \"$(PODS_ROOT)/Headers/Public/React-Core\" \"$(PODS_ROOT)/Headers/Public/React-RCTActionSheet\" \"$(PODS_ROOT)/Headers/Public/React-RCTAnimation\" \"$(PODS_ROOT)/Headers/Public/React-RCTBlob\" \"$(PODS_ROOT)/Headers/Public/React-RCTImage\" \"$(PODS_ROOT)/Headers/Public/React-RCTLinking\" \"$(PODS_ROOT)/Headers/Public/React-RCTNetwork\" \"$(PODS_ROOT)/Headers/Public/React-RCTSettings\" \"$(PODS_ROOT)/Headers/Public/React-RCTText\" \"$(PODS_ROOT)/Headers/Public/React-RCTVibration\" \"$(PODS_ROOT)/Headers/Public/RCT-Folly\" \"$(PODS_ROOT)/Headers/Public/ReactCommon\" \"$(PODS_ROOT)/Headers/Public/ReactCommon/TurbomoduleCore\" \"$(PODS_ROOT)/Headers/Public/ReactCommon/TurbomoduleCore/platform/ios\" \"$(PODS_ROOT)/Headers/Public/ReactCommon/TurbomoduleCore/platform/ios/RCTTurboModule.h\" \"$(PODS_ROOT)/Headers/Public/ReactCommon/TurbomoduleCore/platform/ios/RCTTurboModuleUtils.h\" \"$(PODS_ROOT)/Headers/Public/ReactCommon/TurbomoduleCore/platform/ios/RCTFabricComponentsPlugins.h\"",
      "CLANG_CXX_LANGUAGE_STANDARD" => "c++20",
      "CLANG_CXX_LIBRARY" => "libc++",
      "OTHER_CPLUSPLUSFLAGS" => "-DFOLLY_NO_CONFIG -DFOLLY_MOBILE=1 -DFOLLY_USE_LIBCPP=1 -std=c++20 -DFOLLY_HAS_COROUTINES=0 -DFOLLY_NO_EXCEPTION=1 -DFOLLY_NO_RTTI=1 -DFOLLY_NO_COROUTINES=1",
      "GCC_PREPROCESSOR_DEFINITIONS" => "$(inherited) RCT_NEW_ARCH_ENABLED=1 FOLLY_NO_COROUTINES=1 FOLLY_HAS_COROUTINES=0",
      "OTHER_CFLAGS" => "-DFOLLY_NO_COROUTINES=1 -DFOLLY_HAS_COROUTINES=0",
      "OTHER_CFLAGS_FROM_DRIVER" => "-DFOLLY_NO_COROUTINES=1 -DFOLLY_HAS_COROUTINES=0"
    }
    
    s.pod_target_xcconfig = new_arch_xcconfig
    s.user_target_xcconfig = { 
      "HEADER_SEARCH_PATHS" => "\"$(PODS_ROOT)/boost\"",
      "CLANG_CXX_LANGUAGE_STANDARD" => "c++20" 
    }

    s.dependency "React-Codegen"
    s.dependency "RCT-Folly"
    s.dependency "RCTRequired"
    s.dependency "RCTTypeSafety"
    s.dependency "React"
    s.dependency "ReactCommon"
    s.dependency "React-RCTFabric"
  end
end
