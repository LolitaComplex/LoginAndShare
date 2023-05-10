package com.ymshare.project.login.base

import android.app.Activity
import android.content.Intent
import com.ymshare.project.login.entity.LoginAuthResult
import io.reactivex.rxjava3.core.Observable


interface Auth {

    fun auth(activity: Activity): Observable<LoginAuthResult>

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}

    fun isInstalled(): Boolean
}