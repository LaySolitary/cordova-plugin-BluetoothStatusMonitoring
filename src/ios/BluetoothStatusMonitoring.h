//
//  BlueStatus.h
//  逸身轻
//
//  Created by vivi on 2019/9/6.
//

#import <Cordova/CDV.h>
#import <CoreBluetooth/CoreBluetooth.h>

@interface BluetoothStatusMonitoring : CDVPlugin <CBCentralManagerDelegate>
{
    NSString* stateCallbackId;

}

@property(strong,nonatomic)CBCentralManager  *centralManager;

- (void)coolMethod:(CDVInvokedUrlCommand*)command;

@end 
