//
//  WXApi_Simulator.h
//  RTNWechat
//
//  Created for simulator compatibility
//

#ifndef WXApi_Simulator_h
#define WXApi_Simulator_h

#if TARGET_IPHONE_SIMULATOR

#import "WXApiObject_Simulator.h"

// WXApiDelegate协议
@protocol WXApiDelegate <NSObject>
@optional
- (void)onReq:(BaseReq *)req;
- (void)onResp:(BaseResp *)resp;
@end

// WXApi类模拟实现
@interface WXApi : NSObject

/**
 * 注册应用
 */
+ (BOOL)registerApp:(NSString *)appid universalLink:(NSString *)universalLink;

/**
 * 检查微信是否安装
 */
+ (BOOL)isWXAppInstalled;

/**
 * 发送请求
 */
+ (BOOL)sendReq:(BaseReq *)req completion:(void (^ __nullable)(BOOL success))completion;

/**
 * 处理微信打开应用时的回调
 */
+ (BOOL)handleOpenURL:(NSURL *)url delegate:(id<WXApiDelegate>)delegate;

/**
 * 启用日志
 */
+ (void)startLogByLevel:(WXLogLevel)level logBlock:(void (^)(NSString *log))logCallback;

/**
 * 检查通用链接是否已准备好
 */
+ (void)checkUniversalLinkReady:(void(^)(WXULCheckStep step, WXCheckULStepResult *result))completion;

@end

#endif /* TARGET_IPHONE_SIMULATOR */

#endif /* WXApi_Simulator_h */ 