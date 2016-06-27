# UpdateHandler
Update Checker For Google Play

<b>How to Import the Library:</b>
<a href="https://bintray.com/androidmads/maven/androidmads.updatehandler/_latestVersion"></br>
<img src="https://api.bintray.com/packages/androidmads/maven/androidmads.updatehandler/images/download.svg" /></a></br>
<img href="https://img.shields.io/badge/Android%20Arsenal-UpdateHandler-green.svg"/>
<b>Gradle:</b>
<pre><code>compile 'androidmads.updatehandler:updatehandler:1.0.2'</code></pre>
<b>Maven:</b>
<pre><code>&lt;dependency&gt;
  &lt;groupId&gt;androidmads.updatehandler&lt;/groupId&gt;
  &lt;artifactId&gt;updatehandler&lt;/artifactId&gt;
  &lt;version&gt;1.0.2&lt;/version&gt;
  &lt;type&gt;pom&lt;/type&gt;
&lt;/dependency&gt;</code></pre>
<b>How to use this Library:</b></br>

After importing this library, use the following lines to check version update for your application automatically.
<pre><code>/** 
* This library works in release mode only with the same JKS key used for 
* your Previous Version
*/
UpdateHandler updateHandler = new UpdateHandler(MainActivity.this);
updateHandler.start();
// This is a code to customize the checking count and default count is 5
updateHandler.setCount(2);</code></pre>

#License:
<pre><code>The MIT License (MIT)

Copyright (c) 2016 AndroidMad

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
