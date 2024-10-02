# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keepattributes Signature

# To support retracing of your application's stack traces, you should ensure the build
# retains sufficient information to retrace with by adding the following rules
-keepattributes LineNumberTable,SourceFile
-renamesourcefileattribute SourceFile

-keep class com.google.gson.reflect.TypeToken
-keep class * extends com.google.gson.reflect.TypeToken
-keep public class * implements java.lang.reflect.Type

-keep @kotlinx.parcelize.Parcelize class *
-keepclassmembers class * {
    @kotlinx.parcelize.Parcelize *;
}

-keep public class p3.internal.connection.* {*;}

-keep public class com.test.teleprompter.presentation.navigation.** {*;}

-keep public class com.test.teleprompter.domain.model.** {*;}

-keep public class com.test.teleprompter.data.entity.** {*;}

-keep public class com.test.teleprompter.data.dao.** {*;}
