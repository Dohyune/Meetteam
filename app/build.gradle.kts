import java.util.Properties


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

val properties = Properties()
val localPropertiesFile = project.rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    properties.load(localPropertiesFile.inputStream())
}

val kakaoNativeAppKey = properties.getProperty("kakao_native_app_key", "")
val kakaoOauthHost = properties.getProperty("kakao_oauth_host","")
android {
    namespace = "com.example.meetteam"
    compileSdk = 34

    buildFeatures{
        viewBinding=true
        buildConfig=true
    }
    defaultConfig {
        applicationId = "com.example.meetteam"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String","KAKAO_NATIVE_APP_KEY","\"$kakaoNativeAppKey\"")
        resValue("string","kakao_oauth_host",kakaoOauthHost)
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
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

    //카카오로그인
    implementation ("com.kakao.sdk:v2-all:2.20.3") // 전체 모듈 설치, 2.11.0 버전부터 지원
    implementation ("com.kakao.sdk:v2-user:2.20.3") // 카카오 로그인 API 모듈
    implementation ("com.kakao.sdk:v2-share:2.20.3") // 카카오톡 공유 API 모듈
    implementation ("com.kakao.sdk:v2-talk:2.20.3") // 카카오톡 채널, 카카오톡 소셜, 카카오톡 메시지 API 모듈
    implementation ("com.kakao.sdk:v2-friend:2.20.3") // 피커 API 모듈
    implementation ("com.kakao.sdk:v2-navi:2.20.3") // 카카오내비 API 모듈
    implementation ("com.kakao.sdk:v2-cert:2.20.3") // 카카오톡 인증 서비스 API 모듈

}