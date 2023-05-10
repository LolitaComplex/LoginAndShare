package com.ymshare.project.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory


class WXEntryActivity : Activity(), IWXAPIEventHandler {

    lateinit var api: IWXAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 微信事件回调接口注册
        api = WXAPIFactory.createWXAPI(this, "", false)
        api.handleIntent(this.intent, this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        api.handleIntent(intent, this)
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    override fun onReq(req: BaseReq) {
        when (req.type) {
            ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX -> {}
            ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX -> {}
            else -> {}
        }
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    override fun onResp(resp: BaseResp) {
        when (resp.errCode) {
            BaseResp.ErrCode.ERR_OK -> {
                // 获取code
                val code = (resp as SendAuth.Resp).code
                // 通过code获取授权口令access_token
//                getAccessToken(code)
            }
        }
    }
}