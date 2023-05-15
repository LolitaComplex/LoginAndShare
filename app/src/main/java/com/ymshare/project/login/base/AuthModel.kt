package com.ymshare.project.login.base

sealed class AuthModel {
    data class WXAuthModel(
        val code: String,
        val state: String,
        val language: String,
        val country: String
    ) : AuthModel() {
        companion object {
            const val ERROR_CANCEL = "-1"
        }
    }

    data class QQAuthModel(
        val token: String,
        val expires: String,
        val openId: String
    ) : AuthModel()

    data class SinaAuthModel(
        val accessToken: String,
        val openId: String,
        val expiresTime: Long,
        val screenName: String
    ) : AuthModel()
}
