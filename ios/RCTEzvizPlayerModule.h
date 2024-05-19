//
//  RCTEzvizPlayerModule.h
//  Pods
//
//  Created by 汪志 on 2024/5/19.
//

#ifndef RCTEzvizPlayerModule_h
#define RCTEzvizPlayerModule_h

#import <React/RCTEventEmitter.h>

#import "RCTEzvizPlayerSpecs/RCTEzvizPlayerSpecs.h"
#import <EZOpenSDKFramework/EZPlayer.h>
#import <EZOpenSDKFramework/EZOpenSDK.h>

NS_ASSUME_NONNULL_BEGIN

@interface RCTEzvizPlayerModule : RCTEventEmitter <NativeEzvizVoiceTalkSpec,EZPlayerDelegate>
@property (nonatomic, strong) EZPlayer * talkPlayer;

@end

NS_ASSUME_NONNULL_END

#endif /* RCTEzvizPlayerModule_h */
