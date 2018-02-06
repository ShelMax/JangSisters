package kr.sofac.jangsisters.network;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class ProgressRequestBody extends RequestBody {

    private File file;
    private AddPostPushUpdater pushUpdater;

    public interface UploadCallback {
        void onProgressUpdate(int percentage);
        void onError();
        void onFinish();
    }

    ProgressRequestBody(File file, long totalFilesSize, UploadCallback uploadCallback) {
        this.file = file;
        this.pushUpdater = AddPostPushUpdater.getInstance(uploadCallback, totalFilesSize);
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return MediaType.parse("multipart/form-data");
    }


    @Override
    public void writeTo(@NonNull BufferedSink sink) throws IOException {
        //Размер этого напрямую влияет на скорость загрузки
        byte[] buffer = new byte[3000000];
        FileInputStream in = new FileInputStream(file);
        long uploaded = 0; long uploadedLast = 0;
        Handler handler = new Handler(Looper.getMainLooper());
        int read;
        while ((read = in.read(buffer)) != -1) {
            uploaded += read;
            if((uploaded - uploadedLast) > (contentLength()  / 100 )) {
                // update progress on UI thread
                handler.post(new ProgressUpdater(uploaded - uploadedLast));
                uploadedLast = uploaded;
            }
            sink.write(buffer, 0, read);
        }
    }

    @Override
    public long contentLength() throws IOException {
        return file.length();
    }

    private class ProgressUpdater implements Runnable {

        private long uploaded;

        ProgressUpdater(long uploadedNew) {
            this.uploaded = uploadedNew;
        }

        @Override
        public void run() {
            pushUpdater.updatePush(uploaded);
        }
    }


}
