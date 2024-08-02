package com.example.meetteam

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class KakaoAuthViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "KakaoLoginAuthViewModel"
    }

    private val context = application.applicationContext
    private var activityContext: Activity? = null

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

    fun setActivityContext(activity: Activity) {
        activityContext = activity
    }

    fun handleKakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "Kakao Account login failed", error)
            } else if (token != null) {
                Log.i(TAG, "Kakao Account login success ${token.accessToken}")
                _isLoggedIn.value = true
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            activityContext?.let { activity ->
                UserApiClient.instance.loginWithKakaoTalk(activity) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "Kakaotalk login failed", error)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }
                        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                    } else if (token != null) {
                        Log.i(TAG, "Kakaotalk login success ${token.accessToken}")
                        _isLoggedIn.value = true
                    }
                }
            } ?: run {
                Log.e(TAG, "Activity context is null")
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }
}