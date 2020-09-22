package com.some.mix.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author cyl
 * @date 2020/8/31
 */
public class SaveImageToLocalUtil {

    public static void savePhotoToLocal(final Context context, final Bitmap bitmap, final SaveResultCallback callback){
        final File sdDir = getSDPath();
        if (sdDir == null){
            Toast.makeText(context, "存储不可用", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                File appDir = new File(sdDir, "out_photo");
                if (!appDir.exists()){
                    appDir.mkdir();
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); // 设置当前时间为照片的名称
                String fileName = df.format(new Date()) + ".jpg";
                File file = new File(appDir, fileName);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                    callback.onSaveSuccess();
                } catch (FileNotFoundException e){
                    callback.onSaveFailed();
                    e.printStackTrace();
                } catch (IOException e){
                    callback.onSaveFailed();
                    e.printStackTrace();
                }
                Uri uri = Uri.fromFile(file);
                // 广播通知图库更新
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            }
        });
    }


    public static File getSDPath(){
        File sdDir = null;
        // 判断SD卡是否存在
        boolean sdCardExist = Environment.getExternalStorageState().endsWith(Environment.MEDIA_MOUNTED);
        if (sdCardExist){
            sdDir = Environment.getExternalStorageDirectory(); // 获取根目录
        }
        return sdDir;
    }

    public interface SaveResultCallback{
        void onSaveSuccess();

        void onSaveFailed();
    }
}
