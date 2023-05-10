package com.ymshare.project.login.wx

import android.app.Activity
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.ymshare.project.MainApplication
import com.ymshare.project.login.LoginConfig
import com.ymshare.project.login.base.Auth
import com.ymshare.project.login.base.AuthException
import com.ymshare.project.login.entity.LoginAuthResult
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe

class WeChatAuth : Auth {

    private val mWxApi = WXAPIFactory.createWXAPI(MainApplication.context, LoginConfig.WX_APP_ID)

    private var mSubscriber : ObservableEmitter<LoginAuthResult>? = null

    override fun auth(activity: Activity): Observable<LoginAuthResult> {
        val api = mWxApi

        if (isInstalled()) {
            return Observable.error(AuthException(AuthException.ERROR_UNINSTALL, "未安装微信"))
        }

        var success: Boolean = api.registerApp(LoginConfig.WX_APP_ID)
        if (!success) {
            return Observable.error(AuthException(AuthException.ERROR_AUTH, "微信授权失败"))
        }

        success = api.sendReq(SendAuth.Req().apply {
            scope = "snsapi_userinfo"
            state = "diandi_wx_login"
        })

        return if (!success) {
            Observable.error(AuthException(AuthException.ERROR_AUTH, "微信授权失败"))
        } else {
            Observable.create(ObservableOnSubscribe {
                mSubscriber = it
            })
        }
    }

    override fun isInstalled(): Boolean {
        return mWxApi.isWXAppInstalled
    }


}