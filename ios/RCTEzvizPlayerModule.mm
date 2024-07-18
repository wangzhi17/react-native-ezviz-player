//
//  RCTEzvizPlayerModule.m
//  react-native-ezviz-player
//
//  Created by 汪志 on 2024/5/19.
//

#import <Foundation/Foundation.h>
#import "RCTEzvizPlayerModule.h"

@implementation RCTEzvizPlayerModule {
    BOOL hasListener;
}

NSString *const VOICE_TALK_EVENT = @"VOICE_TALK_EVENT";

RCT_EXPORT_MODULE(EzvizPlayerModule)


- (void)addListener:(NSString *)eventName {
    hasListener = YES;
}

- (void)createVoicePlayer:(NSString *)deviceSerial cameraNo:(NSInteger)cameraNo verifyCode:(NSString *)verifyCode resolve:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject {
    _talkPlayer = [EZOpenSDK createPlayerWithDeviceSerial:deviceSerial cameraNo:cameraNo];
    _talkPlayer.delegate = self;
    [_talkPlayer setPlayVerifyCode:verifyCode];
    resolve(@YES);
}

- (void)destroySDK:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject {
    [EZOpenSDK destoryLib];
    resolve(@YES);
}

- (void)destroyVoiceTalk:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject {
    if (_talkPlayer) {
        [_talkPlayer stopVoiceTalk];
        [EZOpenSDK releasePlayer:_talkPlayer];
        _talkPlayer = nil;

    }
    resolve(@YES);
}
- (void)initSDK:(NSString *)appKey apiUrl:(NSString *)apiUrl resolve:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject{
    if (apiUrl) {
        [EZOpenSDK initLibWithAppKey:appKey url:apiUrl authUrl:@""];
        resolve(@YES);
        return;
    }
    [EZOpenSDK initLibWithAppKey:appKey];
    resolve(@YES);
}

- (void)removeListeners:(NSInteger)count {
    hasListener = NO;
}

- (void)startVoiceTalk:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject {
    if (_talkPlayer) {
        [_talkPlayer closeSound];
        [_talkPlayer startVoiceTalk];
    }
}

- (void)stopVoiceTalk:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject {
    if (_talkPlayer) {
        [_talkPlayer stopVoiceTalk];
    }
    resolve(@YES);
}

- (NSArray<NSString *> *)supportedEvents
{
    return @[VOICE_TALK_EVENT];
}

- (void)player:(EZPlayer *)player didPlayFailed:(NSError *)error
{
    if (hasListener) {
        [self sendEventWithName:VOICE_TALK_EVENT
                           body:@{@"description":[error.userInfo valueForKey:@"ezvizErrorSolution"],
                                  @"sulution":[error.userInfo valueForKey:@"NSLocalizedDescription"],
                                  @"errorCode":[NSNumber numberWithLong:error.code]}];
    }
}

- (void)player:(EZPlayer *)player didReceivedMessage:(NSInteger)messageCode
{
    switch (messageCode) {
        case PLAYER_VOICE_TALK_START:
            if (hasListener) {
                [self sendEventWithName:VOICE_TALK_EVENT
                                   body:@{@"description":@"对讲成功",
                                          @"sulution":@"",
                                          @"errorCode":[NSNumber numberWithInt:0]}];
            }
            break;
        case PLAYER_VOICE_TALK_END:
            if (hasListener) {
                [self sendEventWithName:VOICE_TALK_EVENT
                                   body:@{@"description":@"对讲结束",
                                          @"sulution":@"",
                                          @"errorCode":[NSNumber numberWithInt:-1]}];
            }
            break;

        default:
            break;
    }
}

- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:(const facebook::react::ObjCTurboModule::InitParams &)params
{
    return std::make_shared<facebook::react::NativeEzvizVoiceTalkSpecJSI>(params);
}

@end
