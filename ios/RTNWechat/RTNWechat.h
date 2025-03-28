#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

// 检查是否在模拟器环境
#if TARGET_IPHONE_SIMULATOR
#define IS_SIMULATOR 1
#import "WXApi_Simulator.h"
#else
#define IS_SIMULATOR 0
#import "WXApi.h"
#endif

#if IS_SIMULATOR
@interface RTNWechat : RCTEventEmitter <RCTBridgeModule>
#else
@interface RTNWechat : RCTEventEmitter <RCTBridgeModule, WXApiDelegate>
#endif

@property (nonatomic, copy) NSString *appid;

@end
