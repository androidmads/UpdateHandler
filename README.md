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
