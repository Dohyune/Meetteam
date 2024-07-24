package com.example.meetteam

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.v2.all.BuildConfig


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
        KakaoSdk.init(this, "KAKAO_NATIVE_APP_KEY" )
    }
}