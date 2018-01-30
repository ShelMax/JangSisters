package kr.sofac.jangsisters.utils;

import android.content.Context;
import android.graphics.Bitmap;
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
}
