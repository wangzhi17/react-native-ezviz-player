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

@end
