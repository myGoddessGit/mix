package com.xcj.luck.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream

/**
 * @author cyl
 * @date 2020/8/5
 */
@GlideModule
class CustomAppGlideModule : AppGlideModule(){

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setMemoryCache(LruResourceCache(10 * 1024 * 1024)) // 10MB缓存
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.append(String::class.java, InputStream::class.java, CustomBaseGlideUrlLoader.Factory())
    }
}