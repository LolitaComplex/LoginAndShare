package com.ymshare.project.login.wx

import android.app.Activity
import android.util.Log
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.ymshare.project.MainApplication
import com.ymshare.project.login.LoginAccount
import com.ymshare.project.login.base.Auth
import com.ymshare.project.login.base.AuthException
import com.ymshare.project.login.base.AuthModel
import com.ymshare.project.login.entity.LoginAuthResult
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class WeChatAuth : Auth {

    private val mWxApi = WXAPIFactory.createWXAPI(MainApplication.context, LoginAccount.WX_APP_ID)

    private var mSubscriber : ObservableEmitter<AuthModel.WXAuthModel>? = null

    override fun auth(activity: Activity): Observable<AuthModel.WXAuthModel> {
        val api = mWxApi

        if (!isInstalled()) {
            return Observable.error(AuthException(AuthException.ERROR_UNINSTALL, "未安装微信"))
        }

        var success: Boolean = api.registerApp(LoginAccount.WX_APP_ID)
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
            EventBus.getDefault().register(this)
            Observable.create {
                mSubscriber = it
            }
        }
    }

    override fun isInstalled(): Boolean {
        return mWxApi.isWXAppInstalled
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    fun onMessageEvent(event: AuthModel.WXAuthModel) {
        Log.d("Doing", "Code: ${event.code}")
        if (event.code == AuthModel.WXAuthModel.ERROR_CANCEL) {
            mSubscriber?.onError(AuthException(AuthException.ERROR_AUTH, "取消授权"))
        } else {
            mSubscriber?.onNext(event)
            mSubscriber?.onComplete()
        }

        // TODO 会有更好的解注册地方
        EventBus.getDefault().unregister(this)
    }


}