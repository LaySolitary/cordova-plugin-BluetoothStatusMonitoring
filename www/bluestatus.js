var exec = require('cordova/exec');

exports.getBlutoothStatus = function (arg0, success, error) {
    exec(success, error, 'BluetoothStatusMonitoring', 'getBlutoothStatus', [arg0]);
};
exports.getGPSStatus = function (arg0, success, error) {
    exec(success, error, 'BluetoothStatusMonitoring', 'getGPSStatus', [arg0]);
};
