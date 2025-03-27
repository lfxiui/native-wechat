require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

folly_compiler_flags = '-DFOLLY_NO_CONFIG -DFOLLY_MOBILE=1 -DFOLLY_USE_LIBCPP=1 -Wno-comma -Wno-shorten-64-to-32 -DFOLLY_HAS_COROUTINES=0 -DFOLLY_USE_LIBCPP=1 -DFOLLY_HAS_COROUTINES=0 -DFOLLY_NO_EXCEPTION=1 -DFOLLY_NO_RTTI=1 -DFOLLY_MOBILE=1 -DFOLLY_USE_LIBCPP=1 -DFOLLY_HAS_COROUTINES=0 -DFOLLY_NO_COROUTINES=1 -DFOLLY_HAS_COROUTINES=0 -DFOLLY_NO_COROUTINES=1'

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
  s.dependency "WechatOpenSDK"

  if ENV['RCT_NEW_ARCH_ENABLED'] == '1' then
    s.compiler_flags = folly_compiler_flags + " -DRCT_NEW_ARCH_ENABLED=1"
    s.pod_target_xcconfig    = {
        "HEADER_SEARCH_PATHS" => "\"$(PODS_ROOT)/boost\" \"$(PODS_ROOT)/Headers/Public/ReactCommon\" \"$(PODS_ROOT)/Headers/Public/React\" \"$(PODS_ROOT)/Headers/Public/React-Core\" \"$(PODS_ROOT)/Headers/Public/React-RCTActionSheet\" \"$(PODS_ROOT)/Headers/Public/React-RCTAnimation\" \"$(PODS_ROOT)/Headers/Public/React-RCTBlob\" \"$(PODS_ROOT)/Headers/Public/React-RCTImage\" \"$(PODS_ROOT)/Headers/Public/React-RCTLinking\" \"$(PODS_ROOT)/Headers/Public/React-RCTNetwork\" \"$(PODS_ROOT)/Headers/Public/React-RCTSettings\" \"$(PODS_ROOT)/Headers/Public/React-RCTText\" \"$(PODS_ROOT)/Headers/Public/React-RCTVibration\" \"$(PODS_ROOT)/Headers/Public/RCT-Folly\" \"$(PODS_ROOT)/Headers/Public/ReactCommon\" \"$(PODS_ROOT)/Headers/Public/ReactCommon/TurbomoduleCore\"",
        "CLANG_CXX_LANGUAGE_STANDARD" => "c++20",
        "CLANG_CXX_LIBRARY" => "libc++",
        "OTHER_CPLUSPLUSFLAGS" => "-DFOLLY_NO_CONFIG -DFOLLY_MOBILE=1 -DFOLLY_USE_LIBCPP=1 -std=c++20 -DFOLLY_HAS_COROUTINES=0 -DFOLLY_NO_EXCEPTION=1 -DFOLLY_NO_RTTI=1 -DFOLLY_NO_COROUTINES=1",
        "GCC_PREPROCESSOR_DEFINITIONS" => "$(inherited) RCT_NEW_ARCH_ENABLED=1 FOLLY_NO_COROUTINES=1 FOLLY_HAS_COROUTINES=0"
    }

    s.dependency "React-Codegen"
    s.dependency "RCT-Folly"
    s.dependency "RCTRequired"
    s.dependency "RCTTypeSafety"
    s.dependency "ReactCommon/turbomodule/core"
    s.dependency "ReactCommon"
    s.dependency "React-RCTFabric"
  end

end
