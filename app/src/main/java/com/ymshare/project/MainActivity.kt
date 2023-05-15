package com.ymshare.project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXTextObject
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.ymshare.project.login.PartyAccount
import com.ymshare.project.login.service.LoginPresenter


class MainActivity : AppCompatActivity() {

    private val mPresenter by lazy { LoginPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_login_wechat).setOnClickListener {
            mPresenter.loginWeChat(this)
        }

        findViewById<Button>(R.id.btn_login_qq).setOnClickListener {
            mPresenter.loginQQ(this)
        }

        findViewById<Button>(R.id.btn_login_sina).setOnClickListener {
            mPresenter.loginSina(this)
        }

        findViewById<Button>(R.id.btn_share_wechat).setOnClickListener {
            //初始化一个 WXTextObject 对象，填写分享的文本内容
            val textObj = WXTextObject()
            textObj.text = "测试文本，哈哈哈哈"

            //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
            val msg = WXMediaMessage()
            msg.mediaObject = textObj
            msg.description = "描述信息"

            val req = SendMessageToWX.Req()
            req.transaction = ""
            req.message = msg
            req.scene = SendMessageToWX.Req.WXSceneSession
            //调用api接口，发送数据到微信
            val api = WXAPIFactory.createWXAPI(MainApplication.context, PartyAccount.WX_APP_ID)
            api.sendReq(req)
            
        }

        findViewById<Button>(R.id.btn_share_wechat_circle).setOnClickListener {
            

        }

        findViewById<Button>(R.id.btn_share_qq).setOnClickListener {

        }

        findViewById<Button>(R.id.btn_share_qq_circle).setOnClickListener {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mPresenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }
}