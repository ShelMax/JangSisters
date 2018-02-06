package kr.sofac.jangsisters.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class VideoUtil {

    public static Uri saveVideo(Context activity, Uri videoUri, String videoName, String folder) {
        File directory = activity.getExternalFilesDir(folder);
        ContextWrapper cw = new ContextWrapper(activity);
        File mypath = new File(directory, videoName + ".mp4");

        try {
            FileOutputStream newFile = new FileOutputStream(mypath);
            FileInputStream oldFile = new FileInputStream(FileUtil.getPath(activity, videoUri));

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = oldFile.read(buf)) > 0) {
                newFile.write(buf, 0, len);
            }
            newFile.flush();
            newFile.close();
            oldFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Uri.fromFile(mypath);
    }


}
