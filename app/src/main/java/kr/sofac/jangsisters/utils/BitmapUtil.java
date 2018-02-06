package kr.sofac.jangsisters.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class BitmapUtil {

    public static Bitmap getVideoThumbnailByUrl(String url){
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(url, new HashMap<>());
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

    public static Bitmap getVideoThumbnailByUri(Uri uri, Context context){
        return ThumbnailUtils.createVideoThumbnail(FileUtil.getPath(context, uri), MediaStore.Video.Thumbnails.MINI_KIND);
    }

    public static Bitmap resizeBitmap(Bitmap bitmap) {
        double height = bitmap.getHeight();
        double width = bitmap.getWidth();
        if(height > 1000 || width > 1000) {
            double proportion = height > width ? (1 / (height / 1000)) : (1 / (width / 1000));
            Matrix matrix = new Matrix();
            matrix.postScale((float) proportion, (float) proportion);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        }
        else
            return bitmap;
    }

    public static Uri saveBitmap(Bitmap bitmapImage, Context activity, String folder, String filename) {
        // path to /data/data/yourapp/app_data/imageDir
        File directory = activity.getExternalFilesDir(folder);

        File mypath = new File(directory, filename);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Log.i("TAG", "a new image was created:\n " + directory.getAbsolutePath() + "/" + filename);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "bitmap not saved");
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Uri.fromFile(mypath);
    }
}
