package com.ymshare.project.login.base

import java.lang.Exception

class AuthException(private val code: Int, private val msg: String, cause: Exception = Exception())
    : Exception("ErrorCode: $code, ErrorMsg: $msg", cause) {

    companion object {
        const val ERROR_AUTH = -1;
        const val ERROR_UNINSTALL = -2;
    }


}