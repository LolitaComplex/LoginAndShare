package com.ymshare.project

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.ymshare.project.login.service.LoginPresenter

class MainActivity : AppCompatActivity() {

    private var mShareListener: UMShareListener? = null
    private var mShareAction: ShareAction? = null

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

        findViewById<Button>(R.id.btn_share_wechat).setOnClickListener {

        }

        findViewById<Button>(R.id.btn_share_wechat_circle).setOnClickListener {

        }

        findViewById<Button>(R.id.btn_share_qq).setOnClickListener {

        }

        findViewById<Button>(R.id.btn_share_qq_circle).setOnClickListener {

        }
    }
}