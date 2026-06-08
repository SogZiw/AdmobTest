plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.ad.admob"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.read.pdf.check.aaa.test"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    sourceSets["main"].jniLibs.srcDirs("libs")
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // https://developers.google.com/admob/android/quick-start
    implementation("com.google.android.gms:play-services-ads:24.9.0")
    implementation("com.google.ads.mediation:facebook:6.21.0.1")
    implementation("com.google.ads.mediation:pangle:7.8.5.2.0")
    implementation("com.google.ads.mediation:applovin:13.5.1.0")
    implementation("com.google.ads.mediation:mintegral:17.0.51.0")
    implementation("com.google.ads.mediation:vungle:7.7.0.1")
    implementation("com.unity3d.ads:unity-ads:4.16.6")
    implementation("com.google.ads.mediation:unity:4.16.6.0")

    // TradPlus
    implementation("com.tradplusad:tradplus:15.4.10.1")
    // Admob
    implementation("com.tradplusad:tradplus-googlex:2.15.4.10.1")
    // Meta
    implementation("com.facebook.android:audience-network-sdk:6.21.0")
    implementation("com.tradplusad:tradplus-facebook:1.15.4.10.1")
    // Applovin
    implementation("com.applovin:applovin-sdk:13.5.1")
    implementation("com.tradplusad:tradplus-applovin:9.15.4.10.1")
    implementation("com.google.android.gms:play-services-ads-identifier:18.3.0")
    // Pangle
    implementation("com.tradplusad:tradplus-pangle:19.15.4.10.1")
    implementation("com.pangle.global:pag-sdk:7.8.5.2")
    // Inmobi
    implementation("com.tradplusad:tradplus-inmobix:23.15.4.10.1")
    implementation("com.inmobi.monetization:inmobi-ads-kotlin:11.1.0")
    //optional dependency for better targeting
    implementation("androidx.browser:browser:1.10.0")
    // Fyber
    implementation("com.fyber:marketplace-sdk:8.4.1")
    implementation("com.tradplusad:tradplus-fyber:24.15.4.10.1")
    // Mintegral
    implementation("com.tradplusad:tradplus-mintegralx_overseas:18.15.4.10.1")
    implementation("com.mbridge.msdk.oversea:mbridge_android_sdk:17.0.51")
    // Liftoff
    implementation("com.tradplusad:tradplus-vunglex:7.15.4.10.1")
    implementation("com.vungle:vungle-ads:7.6.2")
    // Bigo
    implementation("com.bigossp:bigo-ads:5.6.2")
    implementation("com.tradplusad:tradplus-bigo:57.15.4.10.1")
    // Cross Promotion
    implementation("com.tradplusad:tradplus-crosspromotion:27.15.4.10.1")
    // TP Exchange
    // 请注意保持与主包版本同步更新
    implementation("com.google.code.gson:gson:2.13.2")
    implementation("com.tradplusad:tp_exchange:40.15.4.10.1")
    // KwaiAds
    implementation("com.tradplusad:tradplus-kwai:75.15.4.10.1")
    implementation("io.github.kwainetwork:adImpl:1.2.21")
    implementation("io.github.kwainetwork:adApi:1.2.21")
    // Moloco
    implementation("com.moloco.sdk:moloco-sdk:4.3.1")
    implementation("com.tradplusad:tradplus-moloco:82.15.4.10.1")

}