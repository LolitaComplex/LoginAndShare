package com.ymshare.project.login.auth.sina

import android.app.Activity
import android.content.Intent
import com.sina.weibo.sdk.auth.AuthInfo
import com.sina.weibo.sdk.openapi.IWBAPI
import com.sina.weibo.sdk.openapi.SdkListener
import com.sina.weibo.sdk.openapi.WBAPIFactory
import com.ymshare.project.MainApplication
import com.ymshare.project.login.PartyAccount
import com.ymshare.project.login.base.Auth
import com.ymshare.project.login.base.AuthException
import com.ymshare.project.login.base.AuthModel
import io.reactivex.rxjava3.core.Observable
import java.lang.Exception

class SinaAuth() : Auth {

    private val wbApi: IWBAPI
    private var mActivity: Activity? = null

    init {
        val authInfo = AuthInfo(MainApplication.context, PartyAccount.SINA_APP_ID,
            PartyAccount.SINA_CALLBACK, PartyAccount.SINA_SCOPE)

        val wbApi = WBAPIFactory.createWBAPI(MainApplication.context) // 传Context即可，不再依赖于Activity
        wbApi.registerApp(MainApplication.context, authInfo, object : SdkListener {
            override fun onInitSuccess() {

            }

            override fun onInitFailure(e: Exception?) {

            }
        })
        this.wbApi = wbApi
    }

    override fun auth(activity: Activity): Observable<AuthModel.SinaAuthModel> {
        if (!isInstalled()) {
            return Observable.error(AuthException(
                AuthException.ERROR_AUTH, "请检查您的Sina是否正确安装"))
        }
        mActivity = activity
        return Observable.create { subscriber ->
            wbApi.authorize(activity, SinaAuthListener(subscriber))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        wbApi.authorizeCallback(mActivity, resultCode, resultCode, data)
    }

    override fun isInstalled(): Boolean {
        return wbApi.isWBAppInstalled
    }
}