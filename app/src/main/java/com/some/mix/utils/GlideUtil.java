package com.some.mix.utils;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.some.mix.R;

/**
 * @author cyl
 * @date 2020/8/19
 */
public class GlideUtil {

    private static RequestOptions normal_image_options = RequestOptions
            .placeholderOf(R.drawable.ic_img_default)
            .error(R.drawable.ic_img_default)
            .centerCrop();

    public static void loadImage(Context context, String path, ImageView imageView){
        Glide.with(context)
                .load(path)
                .apply(normal_image_options)
                .into(imageView);
    }
}
