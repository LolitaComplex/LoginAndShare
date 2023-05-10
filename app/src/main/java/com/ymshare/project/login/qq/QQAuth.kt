package com.ymshare.project.login.qq

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import com.tencent.tauth.Tencent
import com.ymshare.project.MainApplication
import com.ymshare.project.login.LoginConfig
import com.ymshare.project.login.base.Auth
import com.ymshare.project.login.base.AuthException
import com.ymshare.project.login.entity.LoginAuthResult
import io.reactivex.rxjava3.core.Observable

class QQAuth : Auth {

    private var mAuthListener: QQAuthListener? = null

    override fun auth(activity: Activity): Observable<LoginAuthResult> {
        if (!isInstalled()) {
            return Observable.error(AuthException(
                AuthException.ERROR_AUTH, "请检查您的QQ是否正确安装"))
        }
        val instance: Tencent =
            Tencent.createInstance(LoginConfig.QQ_APP_ID, MainApplication.context)
        return Observable.create { subscriber ->
            mAuthListener = QQAuthListener(subscriber, instance)
            instance.login(activity, "all", mAuthListener)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val authListener = mAuthListener?: return

        Tencent.onActivityResultData(requestCode, resultCode, data, authListener)
    }

    override fun isInstalled(): Boolean {
        return try {
            MainApplication.context.packageManager
                .getPackageInfo("com.tencent.mobileqq", 64) != null
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}