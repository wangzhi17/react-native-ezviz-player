//
//  RCTEzvizPlayerManager.mm
//  react-native-ezviz-player
//
//  Created by 汪志 on 2024/5/7.
//

#import <Foundation/Foundation.h>
#import "RCTEzvizPlayerManager.h"
#import "RCTEzvizPlayer.h"

@implementation RCTEzvizPlayerManager

RCT_EXPORT_MODULE(EzvizPlayer)

- (UIView *)view
{
    return [RCTEzvizPlayer new];
}

RCT_CUSTOM_VIEW_PROPERTY(accessToken,NSString,RCTEzvizPlayer)
{
    view.accessToken = [RCTConvert NSString:json];
}

RCT_CUSTOM_VIEW_PROPERTY(deviceSerial,NSString,RCTEzvizPlayer)
{
    view.deviceSerial = [RCTConvert NSString:json];
}
RCT_CUSTOM_VIEW_PROPERTY(verifyCode,NSString,RCTEzvizPlayer)
{
    view.verifyCode = [RCTConvert NSString:json];
}
RCT_CUSTOM_VIEW_PROPERTY(cameraNo,NSInteger,RCTEzvizPlayer)
{
    view.cameraNo = json == nil ? 1 : [RCTConvert NSInteger:json];
}

@end
