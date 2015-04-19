Android Developer Toolbelt
============
On-device low-memory testing for Android.  Fill your phone's RAM and see how your application performs.  [Download the app][Play Store Link], tap the Fill Memory button, and re-open your app - hopefully nothing breaks :)

Why?
------
In my expierence this is where the majority of bugs in Android applications are found.  It's also one of the hardest and most time consuming things to test.  If you're getting large volumes of seemingly un-reproducable crashes in your logs, my bet is it's something to do with your app being put into a background state.

How to Test Your App
------
There are three main ways this tool can be used to test your app:  

1. Testing your Application/Activities being placed into background.  
2. Testing your process being killed (i.e. everything in memory, including all static variables will be removed).  
3. Testing your app while under memory pressure (maybe more useful for games).  

To test cases 1 & 2, use the instructions below.  For testing while under memory pressure, just don't press the stop button (unfortunately this can sometimes lead to freezing issues on some devices)[2].    

1. Start your application and build up some state you want to test i.e. play with it a little.  
2. Start this app & tap the Fill Memory button.  You'll see the memory counter decrease.  When it hits a point when it seemingly cannot go any lower or the low memory indicator is `true`[2], your app has *probably*[3] been placed into a background state.  
3. Press the Stop button and return to your app to ensure all state has been properly restored.  

[1] I'm planning on adding a Pause button soon, so that the memory level can be kept constant - which should make this kind of testing much easier.  

[2] Not all devices will be able to reach the state where the 'Low Memory' indicator is true.  This is an indicator for the operating system as a whole, not specific applications.  Your app can still be placed into background as normal (when the indicator can't seem to go much lower).

[3] Android kills applications using different criteria - I can't guarantee when it will kill your app, the longer you wait, the more likely your app has been killed. When you notice the RAM counter bouncing up and down (~10% mark on a Nexus 5), you know that Android is freeing up memory by killing applications & services.


Under the Hood
-------
If you want to fill an Android device's memory - there are two methods of programatically doing this (that I know of):

1. Use the NDK and `malloc()` to fill the native heap.
2. Use multiple processes and fill the standard Java heap for each one.

I opted for the latter method for two reasons:

1. Easier to control.  Less chance of doing something stupid and having memory that doesn't get cleaned up when finished testing.
2. My experience with similar apps seems to indicate that the native heap hits an upper limit memory usage (~1Gb).  On recent devices, this sometimes isn't enough to trigger the low-memory state.

In order to get the application to run in multiple process I'm using multiple `Services`, each with a seperate `android:process` attribute set in the manifest.  Normally this would mean writing code for ~20 Service classes, but that's where code generation comes to the rescue! The `compiler` module handles generating the source code for these classes.


License
-------

    Copyright 2015 Oisín O'Neill

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[Play Store Link]: https://play.google.com/store/apps/details?id=com.tspoon.androidtoolbelt
