# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/gabrielvilloldo/Documents/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

####################################################
## Android Base Exclutions
####################################################

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keep public class * extends android.app.Application
-keep public class * extends android.app.Activity
-keep public class * extends android.app.PreferenceActivity
-keep public class * extends android.view.View
-keep public class * extends android.widget.BaseAdapter
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * implements android.view.View.OnTouchListener
-keep public class * implements android.view.View.OnClickListener

####################################################
## Android Support Exclutions
####################################################

-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v7.internal.** { *; }
-keep interface android.support.v7.internal.** { *; }
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }
-keep class !android.support.v7.internal.view.menu.**,** {*;}

####################################################
## Google Play Services & Analytics
####################################################

-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}
-keep class com.google.android.gms.** {*;}

-keep class com.google.** {*;}
-keepclassmembers class com.google.** {*;}

####################################################
## Shrink Configurations - Logging
####################################################
-assumenosideeffects class android.util.Log {
    public static *** e(...);
    public static *** w(...);
    public static *** wtf(...);
    public static *** d(...);
    public static *** v(...);
}

