package com.ymshare.project.login.service

import android.app.Activity
import com.ymshare.project.login.entity.LoginAuthResult
import com.ymshare.project.login.qq.QQAuth
import com.ymshare.project.login.wx.WeChatAuth
import io.reactivex.rxjava3.core.Observable

class LoginService {

    private val weChatAuth by lazy { WeChatAuth() }
    private val qqAuth by lazy { QQAuth() }

    fun loginWeChat(activity: Activity): Observable<LoginAuthResult> {
        return weChatAuth.auth(activity)
    }

    fun loginQQ(activity: Activity): Observable<LoginAuthResult> {
        return qqAuth.auth(activity)
    }
}