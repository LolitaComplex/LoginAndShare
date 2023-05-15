package com.ymshare.project.login.service

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.ymshare.project.MainApplication
import com.ymshare.project.login.qq.QQAuth
import com.ymshare.project.login.sina.SinaAuth
import com.ymshare.project.login.wx.WeChatAuth
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginPresenter {

    private val service by lazy { LoginService() }
    private val wxAuth by lazy { WeChatAuth() }
    private val qqAuth by lazy { QQAuth() }
    private val sinaAuth by lazy { SinaAuth() }

    @JvmInline
    value class LoginType(val type: Int)

    companion object {
        private val LOGIN_TYPE_NONE = LoginType(0)
        private val LOGIN_TYPE_WX = LoginType(1)
        private val LOGIN_TYPE_QQ = LoginType(2)
        private val LOGIN_TYPE_SINA = LoginType(3)
    }

    private var mCurrentLoginType = LOGIN_TYPE_NONE

    private val mDisposable = CompositeDisposable()

    fun loginWeChat(activity: Activity) {
        val disposable = wxAuth.auth(activity)
            .doOnSubscribe { setCurrentType(LOGIN_TYPE_WX) }
            .doFinally { setCurrentType(LOGIN_TYPE_NONE) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                Log.d("Doing", "Result: $result")
                Toast.makeText(MainApplication.context, "Result: $result", Toast.LENGTH_LONG).show()
            }, {error ->
                Log.e("Doing", "微信登陆失败", error)
            })

        mDisposable.add(disposable)
    }

    private fun setCurrentType(type: LoginType) {
        mCurrentLoginType = type
    }

    fun loginQQ(activity: Activity) {
        val disposable = qqAuth.auth(activity)
            .doOnSubscribe { setCurrentType(LOGIN_TYPE_QQ) }
            .doFinally { setCurrentType(LOGIN_TYPE_NONE) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                Log.d("Doing", "Result: $result")
                Toast.makeText(MainApplication.context, "Result: $result", Toast.LENGTH_LONG).show()
            }, {error ->
                Log.e("Doing", "QQ登陆失败", error)
            })

        mDisposable.add(disposable)
    }

    fun loginSina(activity: Activity) {
        val disposable = sinaAuth.auth(activity)
            .doOnSubscribe { setCurrentType(LOGIN_TYPE_SINA) }
            .doFinally { setCurrentType(LOGIN_TYPE_NONE) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                Log.d("Doing", "Result: $result")
                Toast.makeText(MainApplication.context, "Result: $result", Toast.LENGTH_LONG).show()
            }, {error ->
                Log.e("Doing", "Sina登陆失败", error)
            })

        mDisposable.add(disposable)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val type = mCurrentLoginType
        when (type) {
            LOGIN_TYPE_WX -> {
                wxAuth.onActivityResult(requestCode, resultCode, data)
            }
            LOGIN_TYPE_QQ -> {
                qqAuth.onActivityResult(requestCode, resultCode, data)
            }
            LOGIN_TYPE_SINA -> {
                sinaAuth.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    fun isQQInstall(): Boolean {
        return qqAuth.isInstalled()
    }

    fun isWxInstall(): Boolean {
        return wxAuth.isInstalled()
    }

    fun isWeiboInstall(): Boolean {
        return sinaAuth.isInstalled()
    }

    fun getLoginType(): LoginType {
        return mCurrentLoginType
    }

    fun onDestroy() {
        mDisposable.clear()
    }

}