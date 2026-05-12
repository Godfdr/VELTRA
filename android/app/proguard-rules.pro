# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# signingConfig, minificationEnabled and shrinkResources flags in build.gradle.

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep NFC classes
-keep class android.nfc.** { *; }
-keep class android.nfc.tech.** { *; }

# Keep Biometric classes
-keep class androidx.biometric.** { *; }

# Keep custom classes
-keep class com.veltra.payment.** { *; }

# OkHttp & Okio (required for network requests to work post-minification)
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# Gson (prevent stripping of serialized model fields)
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Kotlin Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-dontwarn kotlinx.coroutines.**

# AndroidX Security (EncryptedSharedPreferences / EncryptedFile)
-keep class androidx.security.crypto.** { *; }

# Lifecycle ViewModel
-keep class * extends androidx.lifecycle.ViewModel { *; }
-keepclassmembers class * extends androidx.lifecycle.ViewModel { <init>(...); }
