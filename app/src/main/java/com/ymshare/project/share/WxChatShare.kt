package com.ymshare.project.share

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXTextObject
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.ymshare.project.MainApplication
import com.ymshare.project.login.PartyAccount

class WxChatShare private constructor(private val builder: WxChatShare.Builder) : Share {

    private val api = WXAPIFactory.createWXAPI(MainApplication.context, PartyAccount.WX_APP_ID)

    override fun share() {
        val textObj = WXTextObject()
        textObj.text = builder.text

        //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
        val msg = WXMediaMessage()
        msg.mediaObject = textObj
        msg.description = builder.description

        val req = SendMessageToWX.Req()
        req.transaction = builder.transaction
        req.message = msg
        req.scene = SendMessageToWX.Req.WXSceneSession
        //调用api接口，发送数据到微信
        api.sendReq(req)
    }

    class Builder {
        internal var text: String = ""
            private set
        internal var description: String = ""
            private set
        internal var transaction: String = ""
            private set

        fun text(text: String): Builder {
            this.text = text
            return this
        }

        fun description(description: String): Builder {
            this.description = description
            return this
        }

        fun transaction(transaction: String): Builder {
            this.transaction = transaction
            return this
        }

        fun build() = WxChatShare(this)
    }
}