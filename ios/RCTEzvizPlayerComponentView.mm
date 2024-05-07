#import "RCTEzvizPlayerComponentView.h"

#import <react/renderer/components/RCTEzvizPlayerSpecs/ComponentDescriptors.h>
#import <react/renderer/components/RCTEzvizPlayerSpecs/EventEmitters.h>
#import <react/renderer/components/RCTEzvizPlayerSpecs/Props.h>
#import "react/renderer/components/RCTEzvizPlayerSpecs/RCTComponentViewHelpers.h"

#import "RCTFabricComponentsPlugins.h"

using namespace facebook::react;

@interface RCTEzvizPlayerComponentView() <RCTEzvizPlayerViewProtocol>

@end

@implementation RCTEzvizPlayerComponentView {
    
}

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        
    }
    return self;
}

- (void)updateProps:(const facebook::react::Props::Shared &)props oldProps:(const facebook::react::Props::Shared &)oldProps
{
    [super updateProps:props oldProps:oldProps];
}

- (void)closeSound { 
    
}

- (void)createPlayer { 
    
}

- (void)openSound { 
    
}

- (void)releasePlayer { 
    
}

+ (ComponentDescriptorProvider)componentDescriptorProvider {
    return concreteComponentDescriptorProvider<EzvizPlayerComponentDescriptor>();
}

- (void)handleCommand:(nonnull const NSString *)commandName args:(nonnull const NSArray *)args { 
    
}


@end

Class<RCTComponentViewProtocol>RCTEzvizPlayerCls(void)
{
    return RCTEzvizPlayerComponentView.class;
}
