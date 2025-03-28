//
//  WXApi_Simulator.m
//  RTNWechat
//
//  Created for simulator compatibility
//

#import <Foundation/Foundation.h>

#if TARGET_IPHONE_SIMULATOR

#import "WXApi_Simulator.h"

@implementation WXApi

+ (BOOL)registerApp:(NSString *)appid universalLink:(NSString *)universalLink {
    NSLog(@"[模拟器] WXApi registerApp appid=%@ universalLink=%@", appid, universalLink);
    return YES;
}

+ (BOOL)isWXAppInstalled {
    NSLog(@"[模拟器] WXApi isWXAppInstalled 返回NO");
    return NO;
}

+ (BOOL)sendReq:(BaseReq *)req completion:(void (^ __nullable)(BOOL success))completion {
    NSLog(@"[模拟器] WXApi sendReq 模拟操作");
    if (completion) {
        completion(NO);
    }
    return NO;
}

+ (BOOL)handleOpenURL:(NSURL *)url delegate:(id<WXApiDelegate>)delegate {
    NSLog(@"[模拟器] WXApi handleOpenURL url=%@", url);
    return NO;
}

+ (void)startLogByLevel:(WXLogLevel)level logBlock:(void (^)(NSString *log))logCallback {
    NSLog(@"[模拟器] WXApi startLogByLevel level=%d", level);
}

+ (void)checkUniversalLinkReady:(void(^)(WXULCheckStep step, WXCheckULStepResult *result))completion {
    NSLog(@"[模拟器] WXApi checkUniversalLinkReady");
    
    WXCheckULStepResult *result = [[WXCheckULStepResult alloc] init];
    result.success = NO;
    result.suggestion = @"模拟器环境不支持微信SDK";
    result.errorInfo = @"在模拟器中无法使用WechatOpenSDK";
    
    if (completion) {
        completion(WXULCheckStepFinal, result);
    }
}

@end

@implementation WXMediaMessage
+ (WXMediaMessage *)message {
    return [[WXMediaMessage alloc] init];
}

- (void)setThumbData:(NSData *)thumbData {
    // 模拟实现，实际不做操作
}
@end

@implementation WXImageObject
+ (WXImageObject *)object {
    return [[WXImageObject alloc] init];
}
@end

@implementation WXVideoObject
+ (WXVideoObject *)object {
    return [[WXVideoObject alloc] init];
}
@end

@implementation WXWebpageObject
+ (WXWebpageObject *)object {
    return [[WXWebpageObject alloc] init];
}
@end

@implementation WXMiniProgramObject
+ (WXMiniProgramObject *)object {
    return [[WXMiniProgramObject alloc] init];
}
- (void)setHdImageData:(NSData *)hdImageData {
    // 模拟实现，实际不做操作
}
@end

@implementation WXLaunchMiniProgramReq
+ (WXLaunchMiniProgramReq *)object {
    return [[WXLaunchMiniProgramReq alloc] init];
}
@end

@implementation WXCheckULStepResult
@end

@implementation BaseReq
@end

@implementation BaseResp
@end

@implementation SendAuthReq
@end

@implementation SendAuthResp
@end

@implementation SendMessageToWXReq
@end

@implementation WXLaunchMiniProgramResp
@end

@implementation PayReq
@end

@implementation WXSubscribeMsgReq
@end

@implementation WXOpenCustomerServiceReq
@end

@implementation WXApiObject
@end

#endif /* TARGET_IPHONE_SIMULATOR */ 