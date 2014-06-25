//This module only zips a complete directory for Android.
// open a single window
var win=Ti.UI.createWindow({
backgroundColor:'white'
});
var label=Ti.UI.createLabel();
win.add(label);
win.open();
// Create the zip module
var zipmodule=require('com.logic8.zipmodule');
//zip the directory : Application id example: com.logic8.test
zip.zipDirectory("/data/data/[Application id]/app_appdata/"+['Your directory you want to zip'], ['WHERE you want to place the zip file']);
