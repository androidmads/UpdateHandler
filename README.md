# UpdateHandler
Update Checker For Google Play

![Image](https://2.bp.blogspot.com/-vzFWGn1sjwU/V1UJktHJqlI/AAAAAAAAAGA/Vv7kRuyf4IgVW_VNlcCmHJCWDhOYK29fwCLcB/s1024/post_upadate.png)

### Featured In
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-UpdateHandler-green.svg?style=true)](https://android-arsenal.com/details/1/3777)

### Created By
[![API](https://img.shields.io/badge/AndroidMads-AJTS-brightgreen.svg?style=flat)](https://androidmads.blogspot.in/2016/06/automatic-update-checker-for-android.html)

### How to Import the Library:
[![Download](https://api.bintray.com/packages/androidmads/maven/androidmads.updatehandler/images/download.svg?version=1.0.5)](https://bintray.com/androidmads/maven/androidmads.updatehandler/1.0.3/link)

<b>Gradle:</b>
```groovy
compile 'androidmads.updatehandler:updatehandler:1.0.5'
```

<b>Maven:</b>
```groovy
<dependency>
  <groupId>androidmads.updatehandler</groupId>
  <artifactId>updatehandler</artifactId>
  <version>1.0.5</version>
  <type>pom</type>
</dependency>
```
### How to use this Library:

After importing this library, use the following lines to check version update for your application automatically.
```java
/** 
* This library works in release mode only with the same JKS key used for 
* your Previous Version
*/
new UpdateHandler.Builder(this)
	.setContent("New Version Found")
	.setTitle("Update Found")
	.setUpdateText("Yes")
	.setCancelText("No")
	.showDefaultAlert(true)
	.showWhatsNew(true)
	.setCheckerCount(2)
	.setOnUpdateFoundLister(new UpdateHandler.Builder.UpdateListener() {
		@Override
		public void onUpdateFound(boolean newVersion, String whatsNew) {
			tv.setText(tv.getText() + "\n\nUpdate Found : " + newVersion + "\n\nWhat's New\n" + whatsNew);
		}
	})
	.setOnUpdateClickLister(new UpdateHandler.Builder.UpdateClickListener() {
		@Override
		public void onUpdateClick(boolean newVersion, String whatsNew) {
			Log.v("onUpdateClick", String.valueOf(newVersion));
			Log.v("onUpdateClick", whatsNew);
		}
	})
	.setOnCancelClickLister(new UpdateHandler.Builder.UpdateCancelListener() {
		@Override
		public void onCancelClick() {
			Log.v("onCancelClick", "Cancelled");
		}
	})
	.build();
```
# Demo:
<p>Alert Without What' New</p>
<img src = "https://github.com/androidmads/UpdateHandler/blob/master/w_o_whatsnew.png?raw=true" width = "300">
<p>Alert With What' New</p>
<img src = "https://github.com/androidmads/UpdateHandler/blob/master/w_whatsnew.png?raw=true" width = "300">
<p>Update Prompt with Custom Listener</p>
<img src = "https://github.com/androidmads/UpdateHandler/blob/master/w_custom_listener.png?raw=true" width = "300">

#License:
<pre><code>The MIT License (MIT)

Copyright (c) 2017 AndroidMad

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
IN THE SOFTWARE.</code></pre>
