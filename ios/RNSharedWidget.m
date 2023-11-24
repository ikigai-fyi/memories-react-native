//
//  RNSharedWidget.m
//  Memories
//
//  Created by Thomas Mosmant on 22/11/2023.
//

#import <Foundation/Foundation.h>
#import "RNSharedWidget.h"
#import "Memories-Swift.h"

@implementation RNSharedWidget

NSUserDefaults *sharedDefaults;
NSString *appGroup = @"group.ikigai.Memories";

-(dispatch_queue_t)methodQueue {
  return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE(RNSharedWidget)

RCT_EXPORT_METHOD(setData: (NSString *)key: (NSString * )data: (RCTResponseSenderBlock)callback) {
  
  sharedDefaults = [[NSUserDefaults alloc]initWithSuiteName:appGroup];
  
  if(sharedDefaults == nil) {
    callback(@[@0]);
    return;
  }
  
  [sharedDefaults setValue: data forKey:key];
  if (@available(iOS 14, *)) {
    [WidgetKitHelper reloadAllTimelines];
  } else {
    // Fallback on earlier versions
  }
  callback(@[[NSNull null]]);
}

@end
