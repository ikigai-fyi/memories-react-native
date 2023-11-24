//
//  RNSharedWidget.h
//  Memories
//
//  Created by Thomas Mosmant on 22/11/2023.
//

#if __has_include("RCTBridgeModule.h")
#import "RCTBridgeModule.h"
#else
#import <React/RCTBridgeModule.h>
#endif

@interface RNSharedWidget : NSObject<RCTBridgeModule>

@end
