#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

// 检查是否在模拟器环境
#if TARGET_IPHONE_SIMULATOR
#define IS_SIMULATOR 1
#else
#define IS_SIMULATOR 0
#endif

// 根据环境决定是否导入WXApi
#if !IS_SIMULATOR
#import "WXApi.h"
#endif

#if IS_SIMULATOR
@interface RTNWechat : RCTEventEmitter <RCTBridgeModule>
#else
@interface RTNWechat : RCTEventEmitter <RCTBridgeModule, WXApiDelegate>
#endif

@property (nonatomic, copy) NSString *appid;

@end
