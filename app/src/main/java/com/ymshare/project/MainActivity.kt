package com.ymshare.project

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    private var mShareListener: UMShareListener? = null
    private var mShareAction: ShareAction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mShareListener = CustomShareListener(this)
        /*增加自定义按钮的分享面板*/
        /*增加自定义按钮的分享面板*/
        mShareAction = ShareAction(this@MainActivity).setDisplayList(
                SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE,
                SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.WXWORK,
                SHARE_MEDIA.SINA,
                SHARE_MEDIA.QQ,
                SHARE_MEDIA.QZONE,
                SHARE_MEDIA.ALIPAY,
                SHARE_MEDIA.DOUBAN,
                SHARE_MEDIA.SMS,
                SHARE_MEDIA.EMAIL,
                SHARE_MEDIA.YNOTE,
                SHARE_MEDIA.EVERNOTE,
                SHARE_MEDIA.LINKEDIN,
                SHARE_MEDIA.YIXIN,
                SHARE_MEDIA.YIXIN_CIRCLE,
                SHARE_MEDIA.FACEBOOK,
                SHARE_MEDIA.TWITTER,
                SHARE_MEDIA.WHATSAPP,
                SHARE_MEDIA.LINE,
                SHARE_MEDIA.INSTAGRAM,
                SHARE_MEDIA.KAKAO,
                SHARE_MEDIA.PINTEREST,
                SHARE_MEDIA.POCKET,
                SHARE_MEDIA.TUMBLR,
                SHARE_MEDIA.FLICKR,
                SHARE_MEDIA.FOURSQUARE,
                SHARE_MEDIA.MORE
            )
                .addButton("复制文本", "复制文本", "umeng_socialize_copy", "umeng_socialize_copy")
                .addButton(
                    "复制链接",
                    "复制链接",
                    "umeng_socialize_copyurl",
                    "umeng_socialize_copyurl"
                )
                .setShareboardclickCallback { snsPlatform, share_media ->
                    if (snsPlatform.mShowWord == "复制文本") {
                        Toast.makeText(this@MainActivity, "复制文本按钮", Toast.LENGTH_LONG)
                            .show()
                    } else if (snsPlatform.mShowWord == "复制链接") {
                        Toast.makeText(this@MainActivity, "复制链接按钮", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        val web = UMWeb("http://mobile.umeng.com/social")
                        web.title = "来自分享面板标题"
                        web.description = "来自分享面板内容"
                        web.setThumb(UMImage(this@MainActivity, R.drawable.logo))
                        ShareAction(this@MainActivity).withMedia(web)
                            .setPlatform(share_media)
                            .setCallback(mShareListener)
                            .share()
                    }
                }

        findViewById<Button>(R.id.btn_click).setOnClickListener {
            mShareAction?.open()
        }
    }

    private class CustomShareListener(activity: MainActivity) :
        UMShareListener {
        private val mActivity: WeakReference<MainActivity?>

        init {
            mActivity = WeakReference<MainActivity?>(activity)
        }

        override fun onStart(platform: SHARE_MEDIA) {}
        override fun onResult(platform: SHARE_MEDIA) {
            if (platform.name == "WEIXIN_FAVORITE") {
                Toast.makeText(mActivity.get(), "$platform 收藏成功啦", Toast.LENGTH_SHORT).show()
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE && platform != SHARE_MEDIA.TUMBLR && platform != SHARE_MEDIA.POCKET && platform != SHARE_MEDIA.PINTEREST && platform != SHARE_MEDIA.INSTAGRAM && platform != SHARE_MEDIA.YNOTE && platform != SHARE_MEDIA.EVERNOTE) {
                    Toast.makeText(mActivity.get(), "$platform 分享成功啦", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        override fun onError(platform: SHARE_MEDIA, t: Throwable) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                && platform != SHARE_MEDIA.EMAIL && platform != SHARE_MEDIA.FLICKR
                && platform != SHARE_MEDIA.FOURSQUARE && platform != SHARE_MEDIA.TUMBLR
                && platform != SHARE_MEDIA.POCKET && platform != SHARE_MEDIA.PINTEREST
                && platform != SHARE_MEDIA.INSTAGRAM && platform != SHARE_MEDIA.YNOTE
                && platform != SHARE_MEDIA.EVERNOTE) {
                Toast.makeText(mActivity.get(), "$platform 分享失败啦", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onCancel(platform: SHARE_MEDIA) {
            Toast.makeText(mActivity.get(), "$platform 分享取消了", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /** attention to this below ,must add this */
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }

    /**
     * 屏幕横竖屏切换时避免出现window leak的问题
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mShareAction!!.close()
    }

    override fun onDestroy() {
        super.onDestroy()
        UMShareAPI.get(this).release()
    }
}