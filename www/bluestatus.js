var exec = require('cordova/exec');

exports.blueisopen = function (arg0, success, error) {
    exec(success, error, 'BluetoothStatusMonitoring', 'getBlutoothStatus', [arg0]);
};
