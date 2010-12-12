Install
-------

Compile it::

    cd myapp
    ant debug

and add the binary into the ``gh-pages`` branch by::

    ./update_apk

Simulator
---------

Install android simulator, then::

    android

And click "Start". Start a log deameon::

    adb logcat

Finally install the app into the simulator::

    ant install
