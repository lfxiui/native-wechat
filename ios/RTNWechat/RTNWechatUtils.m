//
//  RTNWechatUtils.m
//  CocoaAsyncSocket
//
//  Created by Hector Chong on 10/21/22.
//

#import <Foundation/Foundation.h>
#import "RTNWechatUtils.h"

@implementation RTNWechatUtils

+ (void)downloadFile:(NSURL *)url onSuccess:(void (^)(NSData * _Nullable data))onSuccess onError:(void (^)(NSError *error))onError
{
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:url];
    
    NSURLSessionConfiguration *configuration = [NSURLSessionConfiguration defaultSessionConfiguration];
    NSURLSession *session = [NSURLSession sessionWithConfiguration:configuration];
    
    NSURLSessionDataTask *task = [session dataTaskWithRequest: request completionHandler:^(NSData * _Nullable data, NSURLResponse * _Nullable response, NSError * _Nullable error) {
      if(error){
          onError(error);
      }else{
          onSuccess(data);
      }
    }];
    
    [task resume];
}

+ (void)loadImage:(NSString *)path onSuccess:(void (^)(NSData * _Nullable data))onSuccess onError:(void (^)(NSError *error))onError
{
    // 检查是否是远程URL
    if ([path hasPrefix:@"http://"] || [path hasPrefix:@"https://"]) {
        // 远程图片，使用downloadFile方法
        NSURL *url = [NSURL URLWithString:path];
        if (url) {
            [self downloadFile:url onSuccess:onSuccess onError:onError];
        } else {
            NSError *error = [NSError errorWithDomain:@"RTNWechatErrorDomain" code:1001 userInfo:@{NSLocalizedDescriptionKey: @"无效的URL"}];
            onError(error);
        }
    } else {
        // 本地图片路径处理
        NSString *imagePath = path;
        
        // 处理file://开头的路径
        if ([path hasPrefix:@"file://"]) {
            imagePath = [path substringFromIndex:7]; // 移除"file://"前缀
        }
        
        // 处理相对路径
        if (![imagePath hasPrefix:@"/"]) {
            NSString *documentsDirectory = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES).firstObject;
            imagePath = [documentsDirectory stringByAppendingPathComponent:imagePath];
        }
        
        // 读取本地文件
        NSError *fileError;
        NSData *imageData = [NSData dataWithContentsOfFile:imagePath options:NSDataReadingMappedIfSafe error:&fileError];
        
        if (imageData) {
            onSuccess(imageData);
        } else {
            if (!fileError) {
                fileError = [NSError errorWithDomain:@"RTNWechatErrorDomain" code:1002 userInfo:@{NSLocalizedDescriptionKey: @"无法读取图片数据"}];
            }
            onError(fileError);
        }
    }
}

@end
