#import "RCTEzvizPlayer.h"

#import <react/renderer/components/RCTEzvizPlayerSpecs/ComponentDescriptors.h>
#import <react/renderer/components/RCTEzvizPlayerSpecs/EventEmitters.h>
#import <react/renderer/components/RCTEzvizPlayerSpecs/Props.h>
#import "react/renderer/components/RCTEzvizPlayerSpecs/RCTComponentViewHelpers.h"

#import "RCTFabricComponentsPlugins.h"

using namespace facebook::react;

@interface RCTEzvizPlayer() <RCTEzvizPlayerViewProtocol,EZPlayerDelegate>

@end

@implementation RCTEzvizPlayer {
    EZPlayer *_player;
}

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        static const auto defaultProps = std::make_shared<const EzvizPlayerProps>();
        _props = defaultProps;
    }
    return self;
}

- (void)updateProps:(const facebook::react::Props::Shared &)props oldProps:(const facebook::react::Props::Shared &)oldProps
{
    const auto &oldViewProps = *std::static_pointer_cast<EzvizPlayerProps const>(_props);
    const auto &newViewProps = *std::static_pointer_cast<EzvizPlayerProps const>(props);
    if (oldViewProps.accessToken != newViewProps.accessToken) {
        _accessToken = [[NSString alloc] initWithCString:newViewProps.accessToken.c_str() encoding:NSASCIIStringEncoding];
    }
    if (oldViewProps.deviceSerial != newViewProps.deviceSerial) {
        _deviceSerial = [[NSString alloc] initWithCString:newViewProps.deviceSerial.c_str() encoding:NSASCIIStringEncoding];
    }
    if (oldViewProps.verifyCode != newViewProps.verifyCode) {
        _verifyCode = [[NSString alloc] initWithCString:newViewProps.verifyCode.c_str() encoding:NSASCIIStringEncoding];
    }
    if (oldViewProps.cameraNo != newViewProps.cameraNo) {
        _cameraNo = newViewProps.cameraNo;
    }
    [super updateProps:props oldProps:oldProps];
}

- (void)setDeviceSerial:(NSString *) deviceSerial {
    _deviceSerial = deviceSerial;
}

- (void)setCameraNo:(NSInteger) cameraNo {
    _cameraNo = cameraNo;
}

- (void)setVerifyCode:(NSString *) verifyCode {
    _verifyCode = verifyCode;
}

- (void)setAccessToken:(NSString *) accessToken {
  _accessToken = accessToken;
  [EZOpenSDK setAccessToken:accessToken];
}

- (void)closeSound {
    if (_player) {
        [_player closeSound];
    }
}

- (void)createPlayer {
    if (_player == nil && _deviceSerial != nil && _cameraNo > 0) {
        _player = [EZOpenSDK createPlayerWithDeviceSerial:_deviceSerial cameraNo:_cameraNo];
        _player.delegate = self;
        [_player setPlayerView:self];
        [_player startRealPlay];
    }
}

- (void)openSound {
    if (_player) {
        [_player openSound];
    }
}

- (void)releasePlayer {
    if (_player) {
        [_player stopRealPlay];
        [EZOpenSDK releasePlayer:_player];
        _player = nil;
    }
}

+ (ComponentDescriptorProvider)componentDescriptorProvider {
    return concreteComponentDescriptorProvider<EzvizPlayerComponentDescriptor>();
}

- (void)handleCommand:(nonnull const NSString *)commandName args:(nonnull const NSArray *)args {
    RCTEzvizPlayerHandleCommand(self, commandName, args);
}

- (void)updateEventEmitter:(EventEmitter::Shared const &)eventEmitter
{
    [super updateEventEmitter:eventEmitter];
}

- (void)player:(EZPlayer *)player didPlayFailed:(NSError *)error
{
    if (_eventEmitter) {
        auto viewEventEmitter = std::static_pointer_cast<const EzvizPlayerEventEmitter >(_eventEmitter);
        facebook::react::EzvizPlayerEventEmitter::OnPlayFailed data = {
            .errorCode = int(error.code),
            .solution = std::string([[error.userInfo valueForKey:@"ezvizErrorSolution"] UTF8String]),
            .description = std::string([[error.userInfo valueForKey:@"NSLocalizedDescription"] UTF8String] )
        };
        viewEventEmitter->onPlayFailed(data);
    }
}

Class<RCTComponentViewProtocol>EzvizPlayerCls(void)
{
    return RCTEzvizPlayer.class;
}

@end
