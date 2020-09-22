package com.some.mix.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.some.mix.R;
import com.some.mix.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cyl
 * @date 2020/8/31
 */
public class PhotoBrowserActivity extends FragmentActivity {

    private ImageView imageView;
    private String imageUrl;
    private ArrayList<String> list;
    private String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);
        Intent intent = getIntent();
        if (intent != null){
            imageUrl = intent.getStringExtra("image");
            list = (ArrayList<String>) intent.getSerializableExtra("imageUrl");
            Log.i("数量", list.toString() + "111");
        }
        for (int i = 0 ; i < list.size(); i++){
            url = list.get(i);
            if (url.startsWith("//")){
                url = "https:" + url;
            }
            Log.i("URLURL",url);
        }
        imageView = findViewById(R.id.imageView);
        GlideUtil.loadImage(this, imageUrl, imageView);
    }
}
