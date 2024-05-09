//
//  RCTEzvizPlayer.h
//  Pods
//
//  Created by 汪志 on 2024/5/7.
//

#ifndef RCTEzvizPlayer_h
#define RCTEzvizPlayer_h

#import <UIKit/UIKit.h>
#import <React/RCTViewComponentView.h>

#import <EZOpenSDKFramework/EZPlayer.h>
#import <EZOpenSDKFramework/EZOpenSDK.h>
NS_ASSUME_NONNULL_BEGIN

@interface RCTEzvizPlayer : RCTViewComponentView
@property (nonatomic, copy) NSString *  deviceSerial;
@property (nonatomic, copy) NSString *  verifyCode;
@property (nonatomic, copy) NSString *  accessToken;
@property (nonatomic, assign) NSInteger  cameraNo;

@end

NS_ASSUME_NONNULL_END
#endif /* RCTEzvizPlayer_h */
