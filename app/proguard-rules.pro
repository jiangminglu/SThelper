# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/luffy/Documents/android-sdk-macosx/tools/proguard/proguard-android.txt
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


-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
	static final long serialVersionUID;
	private static final java.io.ObjectStreamField[] serialPersistentFields;
	private void writeObject(java.io.ObjectOutputStream);
	private void readObject(java.io.ObjectInputStream);
	java.lang.Object writeReplace();
	java.lang.Object readResolve();
}


##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson

##---------------End: proguard configuration for Gson  ----------

-keep class com.sthelper.sthelper.bean.* {*;}

##universal-image-loader
-keep public class com.nostra13.universalimageloader.chche.disc.**{*;}
-keep public class com.nostra13.universalimageloader.chche.memory.**{*;}
-keep public class com.nostra13.universalimageloader.core.**{*;}
-keep public class com.nostra13.universalimageloader.utils.**{*;}

#保持枚举 enum 类不被混淆 如果混淆报错，建议使用如下这句话
-keepclassmembers class * implements java.io.Serializable
-keep class com.loopj.android.http.**{*;}
-dontwarn com.tencent.**
-dontwarn javax.xml.**
-dontwarn javax.xml.stream.events.**
-dontwarn com.fasterxml.jackson.databind.**
-dontwarn com.loopj.android.http.**