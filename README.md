Android Developer Toolbelt
============
On-device low-memory testing for Android.  Fill your phone's RAM and see how your application performs.  [Download the app][Play Store Link], tap the Fill Memory button, and re-open your app - hopefully nothing breaks :)

Why?
------
In my expierence this is where the majority of bugs in Android applications are found.  It's also one of the hardest and most time consuming things to test.  If you're getting large volumes of seemingly un-reproducable crashes in your logs, my bet is it's something to do with your app being put into a background state.


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
