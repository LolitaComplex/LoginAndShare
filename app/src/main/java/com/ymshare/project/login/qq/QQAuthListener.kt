package com.ymshare.project.login.qq

import com.tencent.connect.common.Constants
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import com.ymshare.project.login.base.AuthException
import com.ymshare.project.login.entity.LoginAuthResult
import io.reactivex.rxjava3.core.ObservableEmitter
import org.json.JSONException
import org.json.JSONObject


class QQAuthListener(private var mSubscriber: ObservableEmitter<LoginAuthResult>,
     private val mTencent: Tencent) : IUiListener {

    override fun onComplete(response: Any?) {
        if (response !is JSONObject || response.length() == 0) {
            mSubscriber.onError(AuthException(AuthException.ERROR_AUTH, "返回为空，授权失败"))
            return
        }
        doComplete(response)
    }

    private fun doComplete(values: JSONObject) {
        try {
            val token = values.getString(Constants.PARAM_ACCESS_TOKEN)
            val expires = values.getString(Constants.PARAM_EXPIRES_IN)
            val openId = values.getString(Constants.PARAM_OPEN_ID)
            if (token.isNotEmpty() && expires.isNotEmpty() && openId.isNotEmpty()) {
                mTencent.setAccessToken(token, expires)
                mTencent.openId = openId
            }
        } catch (e: JSONException) {
            mSubscriber.onError(AuthException(AuthException.ERROR_AUTH, "解析Json有误，授权失败", e))
        }
    }

    override fun onError(error: UiError?) {
        if (error == null) {
            mSubscriber.onError(AuthException(AuthException.ERROR_AUTH, "授权失败"))
        } else {
            mSubscriber.onError(
                AuthException(AuthException.ERROR_AUTH, "授权失败" + error.errorMessage)
            )
        }
    }

    override fun onCancel() {
        mSubscriber.onError(AuthException(AuthException.ERROR_AUTH, "取消授权"))
    }

    override fun onWarning(p0: Int) {
    }
}