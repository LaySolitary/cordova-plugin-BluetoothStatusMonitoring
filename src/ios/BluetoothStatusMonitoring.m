/********* BlueStatus.m Cordova Plugin Implementation *******/

#import "BluetoothStatusMonitoring.h"
#import <Cordova/CDV.h>

@interface BluetoothStatusMonitoring(){
    NSDictionary *bluetoothStates;
}
@end

@implementation BluetoothStatusMonitoring

- (void)pluginInitialize {
    [super pluginInitialize];
    _centralManager = [[CBCentralManager alloc] initWithDelegate:self queue:nil options:@{CBCentralManagerOptionShowPowerAlertKey: @NO}];
}

- (void)getBlutoothStatus:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
    stateCallbackId=[command.callbackId copy];
    NSString *message=[command.arguments objectAtIndex:0];
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:message];
    [pluginResult setKeepCallbackAsBool:YES];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}


- (void)centralManagerDidUpdateState:(CBCentralManager *)central{
        NSString *message = nil;
        switch (central.state) {
            case 1:
                message = @"该设备不支持蓝牙功能,请检查系统设置";
                break;
            case 2:
                message = @"该设备蓝牙未授权,请检查系统设置";
                break;
            case 3:
                message = @"该设备蓝牙未授权,请检查系统设置";
                break;
            case 4:
                message = @"bluetoothoff";
                break;
            case 5:
                message = @"bluetoothopen";
                break;
                default:
                break;
        }

    if(stateCallbackId!=nil)
    {
        CDVPluginResult* pluginResult = nil;
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:message];
        [pluginResult setKeepCallbackAsBool:YES];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:stateCallbackId];
    }
}
@end

