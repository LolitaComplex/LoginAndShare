package com.ymshare.project.share

interface Share {

    fun share()

    @JvmInline
    value class ShareType(val type: Int)

    companion object {
        val TYPE_TEXT = ShareType(0)
        val TYPE_IMG = ShareType(1)
        val TYPE_VIDEO = ShareType(2)
    }
}