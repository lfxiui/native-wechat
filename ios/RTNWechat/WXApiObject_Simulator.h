//
//  WXApiObject_Simulator.h
//  RTNWechat
//
//  Created for simulator compatibility
//

#ifndef WXApiObject_Simulator_h
#define WXApiObject_Simulator_h

#if TARGET_IPHONE_SIMULATOR

// 提供微信SDK中类的模拟实现，仅用于模拟器编译，不会真正执行

@interface WXApiObject : NSObject
@end

@interface WXMediaMessage : WXApiObject
+ (WXMediaMessage *)message;
- (void)setThumbData:(NSData *)thumbData;
@property (strong, nonatomic) id mediaObject;
@property (copy, nonatomic) NSString *title;
@property (copy, nonatomic) NSString *description;
@end

@interface WXImageObject : WXApiObject
@property (nonatomic, strong) NSData *imageData;
+ (WXImageObject *)object;
@end

@interface WXVideoObject : WXApiObject
@property (nonatomic, copy) NSString *videoUrl;
@property (nonatomic, copy) NSString *videoLowBandUrl;
+ (WXVideoObject *)object;
@end

@interface WXWebpageObject : WXApiObject
@property (nonatomic, copy) NSString *webpageUrl;
+ (WXWebpageObject *)object;
@end

@interface WXMiniProgramObject : WXApiObject
@property (nonatomic, copy) NSString *webpageUrl;
@property (nonatomic, copy) NSString *userName;
@property (nonatomic, copy) NSString *path;
@property (nonatomic, assign) BOOL withShareTicket;
@property (nonatomic, assign) int miniprogramType;
+ (WXMiniProgramObject *)object;
- (void)setHdImageData:(NSData *)hdImageData;
@end

// 请求基类
@interface BaseReq : NSObject
@end

// 响应基类
@interface BaseResp : NSObject
@property (nonatomic, copy) NSString *errStr;
@property (nonatomic, assign) int errCode;
@property (nonatomic, copy) NSString *transaction;
@end

// 支付请求对象
@interface PayReq : BaseReq
@property (nonatomic, copy) NSString *partnerId;
@property (nonatomic, copy) NSString *prepayId;
@property (nonatomic, copy) NSString *nonceStr;
@property (nonatomic, copy) NSString *timeStamp;
@property (nonatomic, copy) NSString *package;
@property (nonatomic, copy) NSString *sign;
@end

// 授权请求对象
@interface SendAuthReq : BaseReq
@property (nonatomic, copy) NSString *scope;
@property (nonatomic, copy) NSString *state;
@end

// 授权响应对象
@interface SendAuthResp : BaseResp
@property (nonatomic, copy) NSString *code;
@property (nonatomic, copy) NSString *state;
@property (nonatomic, copy) NSString *lang;
@property (nonatomic, copy) NSString *country;
@end

// 发送消息请求
@interface SendMessageToWXReq : BaseReq
@property (nonatomic, assign) BOOL bText;
@property (nonatomic, copy) NSString *text;
@property (nonatomic, strong) WXMediaMessage *message;
@property (nonatomic, assign) int scene;
@end

// 小程序请求对象
@interface WXLaunchMiniProgramReq : BaseReq
@property (nonatomic, copy) NSString *userName;
@property (nonatomic, copy) NSString *path;
@property (nonatomic, assign) int miniProgramType;
+ (WXLaunchMiniProgramReq *)object;
@end

// 小程序响应对象
@interface WXLaunchMiniProgramResp : BaseResp
@property (nonatomic, copy) NSString *extMsg;
@end

// 订阅消息请求
@interface WXSubscribeMsgReq : BaseReq
@property (nonatomic, assign) int scene;
@property (nonatomic, copy) NSString *templateId;
@property (nonatomic, copy) NSString *reserved;
@end

// 客服请求对象
@interface WXOpenCustomerServiceReq : BaseReq
@property (nonatomic, copy) NSString *corpid;
@property (nonatomic, copy) NSString *url;
@end

// 定义常量
enum WXScene {
    WXSceneSession = 0,
    WXSceneTimeline = 1,
    WXSceneFavorite = 2,
};

typedef enum {
    WXMiniProgramTypeRelease = 0,
    WXMiniProgramTypeTest = 1,
    WXMiniProgramTypePreview = 2,
} WXMiniProgramType;

typedef enum{
    WXLogLevelDetail = 6,
}WXLogLevel;

typedef enum {
    WXULCheckStepFinal = 3,
}WXULCheckStep;

@interface WXCheckULStepResult : NSObject
@property (nonatomic, assign) BOOL success;
@property (nonatomic, copy) NSString *suggestion;
@property (nonatomic, copy) NSString *errorInfo;
@end

#endif /* TARGET_IPHONE_SIMULATOR */

#endif /* WXApiObject_Simulator_h */ 