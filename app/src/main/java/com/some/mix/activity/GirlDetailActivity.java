package com.some.mix.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.github.chrisbanes.photoview.OnViewTapListener;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallbackWithBeforeParam;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;
import com.some.mix.R;
import com.some.mix.widget.CustomDialog;
import com.some.mix.widget.RoundCornerPhotoView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author cyl
 * @date 2020/8/22
 */
public class GirlDetailActivity  extends FragmentActivity implements View.OnClickListener, View.OnLongClickListener{

    private RoundCornerPhotoView imageView;
    private String url = "";
    private String desc = "";
    private Bitmap mBitmap;
    private BottomSheetDialog dialog;
    private boolean isSure = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girl_detail);
        initData();
        initView();
        loadImage();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    private void initView(){
        imageView = findViewById(R.id.PVImageView);
        findViewById(R.id.iv_topBarBack).setOnClickListener(this);
        ((TextView)findViewById(R.id.tv_topBarText)).setText(desc);
        findViewById(R.id.iv_topBarSearch).setVisibility(View.GONE);
        dialog = new BottomSheetDialog(this);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
    }
    private void initData(){
        Intent intent = getIntent();
        url = intent.getStringExtra("GUrl");
        desc = intent.getStringExtra("GDesc");
    }
    private void loadImage(){
        PhotoViewAttacher mAttacher = new PhotoViewAttacher(imageView);
        mAttacher.setOnLongClickListener(this);
        mAttacher.setOnViewTapListener(new OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                finish();
            }
        });
        Glide.with(this).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                imageView.setImageBitmap(resource);
                mBitmap = resource;
            }
        });
        mAttacher.update();
    }

    private void saveBottomDialog(){
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_save_image, null);
        view.findViewById(R.id.tv_save).setOnClickListener(this);
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        dialog.setContentView(view);
        dialog.show();
    }

    private void saveImageToGallery(Bitmap bitmap){
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsoluteFile();
        if (!file.exists()){ // 如果不存在就创建
            file.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file1 = new File(file, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file1);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        try {
            MediaStore.Images.Media.insertImage(getApplication().getContentResolver(), file1.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        getApplication().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "")));
    }

    private void initPermission(){
        PermissionX.init(this)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onExplainRequestReason(new ExplainReasonCallbackWithBeforeParam() {
                    @Override
                    public void onExplainReason(ExplainScope scope, List<String> deniedList, boolean beforeRequest) {
                        String message = "该功能需要你同意以下权限才能正常使用";
                        CustomDialog customDialog = new CustomDialog(GirlDetailActivity.this, message, deniedList);
                        scope.showRequestReasonDialog(customDialog);
                    }
                })
                .onForwardToSettings(new ForwardToSettingsCallback() {
                    @Override
                    public void onForwardToSettings(ForwardScope scope, List<String> deniedList) {
                        String message = "你需要去设置中手动开启以下权限";
                        CustomDialog customDialog = new CustomDialog(GirlDetailActivity.this, message, deniedList);
                        scope.showForwardToSettingsDialog(customDialog);
                    }
                })
                .request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        isSure = allGranted;
                        if (!allGranted){
                            Toast.makeText(GirlDetailActivity.this,"你拒绝了以下权限: " + deniedList.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_topBarBack:
                finish();
                break;

            case R.id.tv_save:
                initPermission();
                if (isSure){
                    saveImageToGallery(mBitmap);
                    Toast.makeText(GirlDetailActivity.this,"图片已保存",Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
                break;

            case R.id.tv_cancel:
                dialog.dismiss();
                break;

             default:
                 break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.PVImageView && mBitmap != null){
            saveBottomDialog();
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }
}
