package kr.sofac.jangsisters.network;

import android.util.Log;

class AddPostPushUpdater {

    private long totalFilesSize;
    private long totalUploadedSize;
    private long updatedLast;
    private ProgressRequestBody.UploadCallback callback;

    private static AddPostPushUpdater ourInstance;

    static AddPostPushUpdater getInstance(ProgressRequestBody.UploadCallback callback, long totalFilesSize) {
        if(ourInstance == null) {
            ourInstance = new AddPostPushUpdater(callback, totalFilesSize);
        }
        return ourInstance;
    }

    void updatePush(long uploadedNew){
        totalUploadedSize += uploadedNew;
        if((totalUploadedSize - updatedLast) > (totalFilesSize/100)) {
            long percent = (totalUploadedSize * 100) / totalFilesSize;
            callback.onProgressUpdate((int) percent);
            updatedLast = totalUploadedSize;
        }
    }

    private AddPostPushUpdater(ProgressRequestBody.UploadCallback callback, long totalFilesSize) {
        this.totalFilesSize = totalFilesSize;
        this.callback = callback;
        Log.i("TAG", "Okay, new instance totalSize" + totalFilesSize +
                ", uploaded" + totalUploadedSize);
    }
}
