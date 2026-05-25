# Code Citations

## License: unknown
https://github.com/GoogleChrome/developer.chrome.com/blob/51dd7dd5d510ed85d86f5a91cb8fde50b62351c7/site/en/docs/android/trusted-web-activity/integration-guide/index.md

```
Yes, absolutely! Those permissions are **required** for the Android app to work. Here's where to add them:

## **AndroidManifest.xml Setup**

Create or edit `AndroidManifest.xml` in your Android project at:
```
app/src/main/AndroidManifest.xml
```

Add the permissions and feature declarations like this:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.velta.payment">

    <!-- NFC & Biometric Permissions -->
    <uses-permission android:name="android.permission.NFC" />
    <uses-permission android:name="android.permission.USE_BIOMETRIC" />
    <uses-permission android:name="android.permission.INTERNET" />
    
    <!-- NFC Hardware Feature (required) -->
    <uses-feature
        android:name="android.hardware.nfc"
        android:required="true" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.VeltaPayment">

        <activity
            android:name=".VeltaNFCPaymentActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.nfc.action.TECH_DISCOVERED" />
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
            <meta-data
                android:name="android.nfc.action.TECH_DISCOVERED"
                android:resource="@xml/nfc_tech_filter" />
        </activity>

    </application>

</manifest>
```

## **Create NFC Tech Filter XML**

Also create `res/xml/nfc_tech_filter.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:xliff="urn:oasis:names:tc:xliff:document:1.2">
    <tech-list>
        <tech>android.nfc.tech.IsoDep</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcA</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcB</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcF</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcV</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.Ndef</tech>
    </tech-list>
</resources>
```

## **Runtime Permissions (Android 6.0+)**

For Android 6.0+ (API 23+), also request permissions at runtime in your Activity:

```kotlin
// In VeltaNFCPaymentActivity.
```


## License: MIT
https://github.com/tlodge/react-native-rfid-nfc-scanner/blob/5e2db4073de940485191a694d7830c4384433f30/README.md

```
Yes, absolutely! Those permissions are **required** for the Android app to work. Here's where to add them:

## **AndroidManifest.xml Setup**

Create or edit `AndroidManifest.xml` in your Android project at:
```
app/src/main/AndroidManifest.xml
```

Add the permissions and feature declarations like this:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.velta.payment">

    <!-- NFC & Biometric Permissions -->
    <uses-permission android:name="android.permission.NFC" />
    <uses-permission android:name="android.permission.USE_BIOMETRIC" />
    <uses-permission android:name="android.permission.INTERNET" />
    
    <!-- NFC Hardware Feature (required) -->
    <uses-feature
        android:name="android.hardware.nfc"
        android:required="true" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.VeltaPayment">

        <activity
            android:name=".VeltaNFCPaymentActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.nfc.action.TECH_DISCOVERED" />
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
            <meta-data
                android:name="android.nfc.action.TECH_DISCOVERED"
                android:resource="@xml/nfc_tech_filter" />
        </activity>

    </application>

</manifest>
```

## **Create NFC Tech Filter XML**

Also create `res/xml/nfc_tech_filter.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:xliff="urn:oasis:names:tc:xliff:document:1.2">
    <tech-list>
        <tech>android.nfc.tech.IsoDep</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcA</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcB</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcF</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcV</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.Ndef</tech>
    </tech-list>
</resources>
```

## **Runtime Permissions (Android 6.0+)**

For Android 6.0+ (API 23+), also request permissions at runtime in your Activity:

```kotlin
// In VeltaNFCPaymentActivity.kt
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    // Request NFC permission
    if (Build.VERSION.SDK_INT >= Build
```


## License: MIT
https://github.com/tlodge/react-native-rfid-nfc-scanner/blob/5e2db4073de940485191a694d7830c4384433f30/README.md

```
Yes, absolutely! Those permissions are **required** for the Android app to work. Here's where to add them:

## **AndroidManifest.xml Setup**

Create or edit `AndroidManifest.xml` in your Android project at:
```
app/src/main/AndroidManifest.xml
```

Add the permissions and feature declarations like this:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.velta.payment">

    <!-- NFC & Biometric Permissions -->
    <uses-permission android:name="android.permission.NFC" />
    <uses-permission android:name="android.permission.USE_BIOMETRIC" />
    <uses-permission android:name="android.permission.INTERNET" />
    
    <!-- NFC Hardware Feature (required) -->
    <uses-feature
        android:name="android.hardware.nfc"
        android:required="true" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.VeltaPayment">

        <activity
            android:name=".VeltaNFCPaymentActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.nfc.action.TECH_DISCOVERED" />
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
            <meta-data
                android:name="android.nfc.action.TECH_DISCOVERED"
                android:resource="@xml/nfc_tech_filter" />
        </activity>

    </application>

</manifest>
```

## **Create NFC Tech Filter XML**

Also create `res/xml/nfc_tech_filter.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:xliff="urn:oasis:names:tc:xliff:document:1.2">
    <tech-list>
        <tech>android.nfc.tech.IsoDep</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcA</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcB</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcF</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcV</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.Ndef</tech>
    </tech-list>
</resources>
```

## **Runtime Permissions (Android 6.0+)**

For Android 6.0+ (API 23+), also request permissions at runtime in your Activity:

```kotlin
// In VeltaNFCPaymentActivity.kt
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    // Request NFC permission
    if (Build.VERSION.SDK_INT >= Build
```


## License: unknown
https://github.com/aheadlcx/myBlog/blob/8c1f348c13ad866e728b80099dfa8306ea84afbc/source/_posts/%E5%AE%89%E5%8D%93%E4%B8%8A%E9%9D%A2%E7%9A%84NFC%E7%AE%80%E5%8D%95%E5%BA%94%E7%94%A8%E5%AE%9E%E4%BE%8B.md

```
Yes, absolutely! Those permissions are **required** for the Android app to work. Here's where to add them:

## **AndroidManifest.xml Setup**

Create or edit `AndroidManifest.xml` in your Android project at:
```
app/src/main/AndroidManifest.xml
```

Add the permissions and feature declarations like this:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.velta.payment">

    <!-- NFC & Biometric Permissions -->
    <uses-permission android:name="android.permission.NFC" />
    <uses-permission android:name="android.permission.USE_BIOMETRIC" />
    <uses-permission android:name="android.permission.INTERNET" />
    
    <!-- NFC Hardware Feature (required) -->
    <uses-feature
        android:name="android.hardware.nfc"
        android:required="true" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.VeltaPayment">

        <activity
            android:name=".VeltaNFCPaymentActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.nfc.action.TECH_DISCOVERED" />
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
            <meta-data
                android:name="android.nfc.action.TECH_DISCOVERED"
                android:resource="@xml/nfc_tech_filter" />
        </activity>

    </application>

</manifest>
```

## **Create NFC Tech Filter XML**

Also create `res/xml/nfc_tech_filter.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:xliff="urn:oasis:names:tc:xliff:document:1.2">
    <tech-list>
        <tech>android.nfc.tech.IsoDep</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcA</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcB</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcF</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcV</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.Ndef</tech>
    </tech-list>
</resources>
```

## **Runtime Permissions (Android 6.0+)**

For Android 6.0+ (API 23+), also request permissions at runtime in your Activity:

```kotlin
// In VeltaNFCPaymentActivity.kt
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    // Request NFC permission
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.NFC,
                android.Manifest.permission.USE_BIOMETRIC
            ),
            123
        )
    }
    
    initializeNFC()
}
```

## **What Each Permission Does:**

|
```


## License: unknown
https://github.com/cobras9/Test/blob/3f4411ad4f9b963dbe972632bc8df944d4f07bb0/MMWalletAndroid/res/xml/nfc_tech_filter

```
Yes, absolutely! Those permissions are **required** for the Android app to work. Here's where to add them:

## **AndroidManifest.xml Setup**

Create or edit `AndroidManifest.xml` in your Android project at:
```
app/src/main/AndroidManifest.xml
```

Add the permissions and feature declarations like this:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.velta.payment">

    <!-- NFC & Biometric Permissions -->
    <uses-permission android:name="android.permission.NFC" />
    <uses-permission android:name="android.permission.USE_BIOMETRIC" />
    <uses-permission android:name="android.permission.INTERNET" />
    
    <!-- NFC Hardware Feature (required) -->
    <uses-feature
        android:name="android.hardware.nfc"
        android:required="true" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.VeltaPayment">

        <activity
            android:name=".VeltaNFCPaymentActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.nfc.action.TECH_DISCOVERED" />
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
            <meta-data
                android:name="android.nfc.action.TECH_DISCOVERED"
                android:resource="@xml/nfc_tech_filter" />
        </activity>

    </application>

</manifest>
```

## **Create NFC Tech Filter XML**

Also create `res/xml/nfc_tech_filter.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:xliff="urn:oasis:names:tc:xliff:document:1.2">
    <tech-list>
        <tech>android.nfc.tech.IsoDep</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcA</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcB</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcF</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcV</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.Ndef</tech>
    </tech-list>
</resources>
```

## **Runtime Permissions (Android 6.0+)**

For Android 6.0+ (API 23+), also request permissions at runtime in your Activity:

```kotlin
// In VeltaNFCPaymentActivity.kt
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    // Request NFC permission
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.NFC,
                android.Manifest.permission.USE_BIOMETRIC
            ),
            123
        )
    }
    
    initializeNFC()
}
```

## **What Each Permission Does:**

|
```


## License: unknown
https://github.com/cobras9/Test/blob/3f4411ad4f9b963dbe972632bc8df944d4f07bb0/MMWalletAndroid/res/xml/nfc_tech_filter

```
Yes, absolutely! Those permissions are **required** for the Android app to work. Here's where to add them:

## **AndroidManifest.xml Setup**

Create or edit `AndroidManifest.xml` in your Android project at:
```
app/src/main/AndroidManifest.xml
```

Add the permissions and feature declarations like this:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.velta.payment">

    <!-- NFC & Biometric Permissions -->
    <uses-permission android:name="android.permission.NFC" />
    <uses-permission android:name="android.permission.USE_BIOMETRIC" />
    <uses-permission android:name="android.permission.INTERNET" />
    
    <!-- NFC Hardware Feature (required) -->
    <uses-feature
        android:name="android.hardware.nfc"
        android:required="true" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.VeltaPayment">

        <activity
            android:name=".VeltaNFCPaymentActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.nfc.action.TECH_DISCOVERED" />
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
            <meta-data
                android:name="android.nfc.action.TECH_DISCOVERED"
                android:resource="@xml/nfc_tech_filter" />
        </activity>

    </application>

</manifest>
```

## **Create NFC Tech Filter XML**

Also create `res/xml/nfc_tech_filter.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:xliff="urn:oasis:names:tc:xliff:document:1.2">
    <tech-list>
        <tech>android.nfc.tech.IsoDep</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcA</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcB</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcF</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcV</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.Ndef</tech>
    </tech-list>
</resources>
```

## **Runtime Permissions (Android 6.0+)**

For Android 6.0+ (API 23+), also request permissions at runtime in your Activity:

```kotlin
// In VeltaNFCPaymentActivity.kt
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    // Request NFC permission
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.NFC,
                android.Manifest.permission.USE_BIOMETRIC
            ),
            123
        )
    }
    
    initializeNFC()
}
```

## **What Each Permission Does:**

|
```


## License: unknown
https://github.com/aheadlcx/myBlog/blob/8c1f348c13ad866e728b80099dfa8306ea84afbc/source/_posts/%E5%AE%89%E5%8D%93%E4%B8%8A%E9%9D%A2%E7%9A%84NFC%E7%AE%80%E5%8D%95%E5%BA%94%E7%94%A8%E5%AE%9E%E4%BE%8B.md

```
Yes, absolutely! Those permissions are **required** for the Android app to work. Here's where to add them:

## **AndroidManifest.xml Setup**

Create or edit `AndroidManifest.xml` in your Android project at:
```
app/src/main/AndroidManifest.xml
```

Add the permissions and feature declarations like this:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.velta.payment">

    <!-- NFC & Biometric Permissions -->
    <uses-permission android:name="android.permission.NFC" />
    <uses-permission android:name="android.permission.USE_BIOMETRIC" />
    <uses-permission android:name="android.permission.INTERNET" />
    
    <!-- NFC Hardware Feature (required) -->
    <uses-feature
        android:name="android.hardware.nfc"
        android:required="true" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.VeltaPayment">

        <activity
            android:name=".VeltaNFCPaymentActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.nfc.action.TECH_DISCOVERED" />
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
            <meta-data
                android:name="android.nfc.action.TECH_DISCOVERED"
                android:resource="@xml/nfc_tech_filter" />
        </activity>

    </application>

</manifest>
```

## **Create NFC Tech Filter XML**

Also create `res/xml/nfc_tech_filter.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:xliff="urn:oasis:names:tc:xliff:document:1.2">
    <tech-list>
        <tech>android.nfc.tech.IsoDep</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcA</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcB</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcF</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcV</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.Ndef</tech>
    </tech-list>
</resources>
```

## **Runtime Permissions (Android 6.0+)**

For Android 6.0+ (API 23+), also request permissions at runtime in your Activity:

```kotlin
// In VeltaNFCPaymentActivity.kt
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    // Request NFC permission
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.NFC,
                android.Manifest.permission.USE_BIOMETRIC
            ),
            123
        )
    }
    
    initializeNFC()
}
```

## **What Each Permission Does:**

|
```


## License: unknown
https://github.com/cobras9/Test/blob/3f4411ad4f9b963dbe972632bc8df944d4f07bb0/MMWalletAndroid/res/xml/nfc_tech_filter

```
Yes, absolutely! Those permissions are **required** for the Android app to work. Here's where to add them:

## **AndroidManifest.xml Setup**

Create or edit `AndroidManifest.xml` in your Android project at:
```
app/src/main/AndroidManifest.xml
```

Add the permissions and feature declarations like this:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.velta.payment">

    <!-- NFC & Biometric Permissions -->
    <uses-permission android:name="android.permission.NFC" />
    <uses-permission android:name="android.permission.USE_BIOMETRIC" />
    <uses-permission android:name="android.permission.INTERNET" />
    
    <!-- NFC Hardware Feature (required) -->
    <uses-feature
        android:name="android.hardware.nfc"
        android:required="true" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.VeltaPayment">

        <activity
            android:name=".VeltaNFCPaymentActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.nfc.action.TECH_DISCOVERED" />
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
            <meta-data
                android:name="android.nfc.action.TECH_DISCOVERED"
                android:resource="@xml/nfc_tech_filter" />
        </activity>

    </application>

</manifest>
```

## **Create NFC Tech Filter XML**

Also create `res/xml/nfc_tech_filter.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:xliff="urn:oasis:names:tc:xliff:document:1.2">
    <tech-list>
        <tech>android.nfc.tech.IsoDep</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcA</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcB</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcF</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcV</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.Ndef</tech>
    </tech-list>
</resources>
```

## **Runtime Permissions (Android 6.0+)**

For Android 6.0+ (API 23+), also request permissions at runtime in your Activity:

```kotlin
// In VeltaNFCPaymentActivity.kt
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    // Request NFC permission
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.NFC,
                android.Manifest.permission.USE_BIOMETRIC
            ),
            123
        )
    }
    
    initializeNFC()
}
```

## **What Each Permission Does:**

|
```


## License: unknown
https://github.com/aheadlcx/myBlog/blob/8c1f348c13ad866e728b80099dfa8306ea84afbc/source/_posts/%E5%AE%89%E5%8D%93%E4%B8%8A%E9%9D%A2%E7%9A%84NFC%E7%AE%80%E5%8D%95%E5%BA%94%E7%94%A8%E5%AE%9E%E4%BE%8B.md

```
Yes, absolutely! Those permissions are **required** for the Android app to work. Here's where to add them:

## **AndroidManifest.xml Setup**

Create or edit `AndroidManifest.xml` in your Android project at:
```
app/src/main/AndroidManifest.xml
```

Add the permissions and feature declarations like this:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.velta.payment">

    <!-- NFC & Biometric Permissions -->
    <uses-permission android:name="android.permission.NFC" />
    <uses-permission android:name="android.permission.USE_BIOMETRIC" />
    <uses-permission android:name="android.permission.INTERNET" />
    
    <!-- NFC Hardware Feature (required) -->
    <uses-feature
        android:name="android.hardware.nfc"
        android:required="true" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.VeltaPayment">

        <activity
            android:name=".VeltaNFCPaymentActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.nfc.action.TECH_DISCOVERED" />
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
            <meta-data
                android:name="android.nfc.action.TECH_DISCOVERED"
                android:resource="@xml/nfc_tech_filter" />
        </activity>

    </application>

</manifest>
```

## **Create NFC Tech Filter XML**

Also create `res/xml/nfc_tech_filter.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:xliff="urn:oasis:names:tc:xliff:document:1.2">
    <tech-list>
        <tech>android.nfc.tech.IsoDep</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcA</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcB</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcF</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.NfcV</tech>
    </tech-list>
    <tech-list>
        <tech>android.nfc.tech.Ndef</tech>
    </tech-list>
</resources>
```

## **Runtime Permissions (Android 6.0+)**

For Android 6.0+ (API 23+), also request permissions at runtime in your Activity:

```kotlin
// In VeltaNFCPaymentActivity.kt
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    // Request NFC permission
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.NFC,
                android.Manifest.permission.USE_BIOMETRIC
            ),
            123
        )
    }
    
    initializeNFC()
}
```

## **What Each Permission Does:**

|
```

