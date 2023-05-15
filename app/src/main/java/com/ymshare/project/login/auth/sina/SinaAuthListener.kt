package com.ymshare.project.login.auth.sina

import com.sina.weibo.sdk.auth.Oauth2AccessToken
import com.sina.weibo.sdk.auth.WbAuthListener
import com.sina.weibo.sdk.common.UiError
import com.ymshare.project.login.base.AuthException
import com.ymshare.project.login.base.AuthModel
import io.reactivex.rxjava3.core.ObservableEmitter


class SinaAuthListener(private val mSubscriber: ObservableEmitter<AuthModel.SinaAuthModel>) : WbAuthListener {

    override fun onComplete(token: Oauth2AccessToken?) {
        if (token == null) {
            mSubscriber.onError(
                AuthException(AuthException.ERROR_AUTH, "授权失败")
            )
            return
        }

        val accessToken = token.accessToken
        if (accessToken.isNullOrEmpty() || !token.isSessionValid) {
            mSubscriber.onError(AuthException(AuthException.ERROR_AUTH, "授权失败"))
            return
        }

        val openId = token.uid
        val screenName = token.screenName
        val expiresTime = token.expiresTime
        mSubscriber.onNext(AuthModel.SinaAuthModel(accessToken, openId, expiresTime, screenName))
        mSubscriber.onComplete()
    }

    override fun onError(p0: UiError?) {
        mSubscriber.onError(AuthException(AuthException.ERROR_AUTH, "授权失败: ${p0?.errorMessage}"))
    }

    override fun onCancel() {
        mSubscriber.onError(AuthException(AuthException.ERROR_AUTH, "授权失败: 用户取消"))
    }
}