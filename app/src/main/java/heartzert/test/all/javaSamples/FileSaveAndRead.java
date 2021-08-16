package heartzert.test.all.javaSamples;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by heartzert on 8/16/21.
 * Email: heartzert@163.com
 */
class FileSaveAndRead {

    /**
     * /sdcard/、/storage/self/primary/ 是软连接，真正指向的是/storage/emulated/0/
     */

    //一些路径
    void androidPath(Context context) {

        //================内部存储================
        //私有文件路径
        //即/data/user/0/包名/files/
        //即/data/data/包名/files/
        File filePath = context.getFilesDir();

        //私有缓存路径
        //即/data/user/0/包名/files/
        //Device File Explorer中为/data/data/包名/files/
        File cachePath = context.getCacheDir();

        //私有代码优化文件路径
        File codeCachePath = context.getCodeCacheDir();

        //私有文件夹路径
        File rootPath = context.getDataDir();
        //========================================

        //================外部存储================
        //外部私有根目录
        //即/sdcard/Android/data/包名/
        File fileDir = context.getExternalFilesDir(null);

        //指定目录，自动生成对应的子目录
        //即/sdcard/Android/data/包名/DCIM/
        File fileDir2 = context.getExternalFilesDir(Environment.DIRECTORY_DCIM);

        //还可以指定自定的子文件夹
        //即/sdcard/Android/data/包名/test/
        File fileDir3 = context.getExternalFilesDir("test");

        //外部所有私有目录
        File[] fileList = context.getExternalFilesDirs(null);

        //外部缓存
        File cacheDir = context.getExternalCacheDir();

        //API>=19
        File[] cacheList = context.getExternalCacheDirs();

        //========================================

    }

    //普通写文件方法
    void writeFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) return;
        try {
            File file = new File(filePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
            String writeContent = "hello world\n";
            bos.write(writeContent.getBytes());
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //写法简单点的写文件方法
    void writeFile2(Context context, String filePath) {
        if (TextUtils.isEmpty(filePath)) return;
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(filePath, Context.MODE_PRIVATE);
            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
            String writeContent = "hello world\n";
            bos.write(writeContent.getBytes());
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //普通写文件方法，同样也有简单写法
    void readFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) return;
        try {
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fileInputStream);
            byte[] readContent = new byte[1024];
            int readLen = 0;
            while (readLen != -1) {
                readLen = bis.read(readContent, 0, readContent.length);
                if (readLen > 0) {
                    String content = new String(readContent);
                    Log.d("test", "read content:" + content.substring(0, readLen));
                }
            }
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //读取公共存储的图片
    private void getImagePath(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while(cursor.moveToNext()) {
            String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
        }
    }
}
