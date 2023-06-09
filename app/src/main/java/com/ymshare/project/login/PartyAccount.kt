package com.ymshare.project.login

object PartyAccount {

    const val WX_APP_ID = "wx28aebd63a75d552e"

    const val QQ_APP_ID = "1104658198"

    const val SINA_APP_ID = "3414846017"
    /**
    * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
    * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利 选择赋予应用的功能。
    * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的 使用权限，高级权限需要进行申请。 目前
    * Scope 支持传入多个 Scope 权限，用逗号分隔。 有关哪些 OpenAPI
    * 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI 关于 Scope
    * 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
    */
    const val SINA_SCOPE = "email,direct_messages_read,direct_messages_write,friendships_groups_read," +
            "friendships_groups_write,statuses_to_me_read," +
            "invitation_write,follow_app_official_microblog"

    const val SINA_CALLBACK = "http://www.meelive.cn/ticker/invoke.html";

}