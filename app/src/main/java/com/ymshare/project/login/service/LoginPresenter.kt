package com.ymshare.project.login.service

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginPresenter {

    private val service by lazy { LoginService() }

    fun loginWeChat(activity: Activity) {
        val subscription = service.loginWeChat(activity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                Log.d("Doing", "Result: $result")
            }, {error ->
                Log.e("Doing", "微信登陆失败", error)
            })

        // TODO Subscription根据Activity生命周期做销毁
    }

    fun loginQQ(activity: Activity) {
        val subscription = service.loginQQ(activity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                Log.d("Doing", "Result: $result")
            }, {error ->
                Log.e("Doing", "QQ登陆失败", error)
            })

        // TODO Subscription根据Activity生命周期做销毁
    }
}