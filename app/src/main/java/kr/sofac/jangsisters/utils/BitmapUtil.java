package kr.sofac.jangsisters.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;

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
        return ThumbnailUtils.createVideoThumbnail(PathUtil.getPath(context, uri), MediaStore.Video.Thumbnails.MINI_KIND);
    }

    public static Bitmap resizeBitmap(Bitmap bitmap) {
        double height = bitmap.getHeight();
        double width = bitmap.getWidth();
        double proportion = height > width ? (1 / (height / 1000)) : (1 / (width / 1000));
        Matrix matrix = new Matrix();
        matrix.postScale((float) proportion, (float) proportion);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }
}
