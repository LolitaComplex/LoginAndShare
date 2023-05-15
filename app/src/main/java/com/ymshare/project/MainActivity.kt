package com.ymshare.project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ymshare.project.login.presenter.LoginPresenter
import com.ymshare.project.share.ShareHandler


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
            ShareHandler.wxChat()
                .text("测试文本，哈哈哈哈")
                .description("描述信息")
                .build()
                .share()
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