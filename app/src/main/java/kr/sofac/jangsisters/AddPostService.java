package kr.sofac.jangsisters;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kr.sofac.jangsisters.config.KeyTransferObj;
import kr.sofac.jangsisters.network.Connection;
import kr.sofac.jangsisters.network.ProgressRequestBody;
import kr.sofac.jangsisters.network.dto.AddPostDTO;
import kr.sofac.jangsisters.network.dto.SenderContainerDTO;
import kr.sofac.jangsisters.utils.BitmapUtil;
import kr.sofac.jangsisters.utils.VideoUtil;

public class AddPostService extends Service implements ProgressRequestBody.UploadCallback{

    private NotificationManager notificationManager;
    private NotificationChannel notificationChannel;
    private NotificationCompat.Builder builder;
    private int id = 1;
    private String channelID = "AddPostChannelID";
    private String channelName = "AddPostChannelName";
    private List<Uri> uris;

    public AddPostService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this, channelID);
        builder.setContentTitle("Adding post")
                .setContentText("Adding in progress")
                .setSmallIcon(R.drawable.icon);
        if(Build.VERSION.SDK_INT >= 26) {
            notificationChannel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            // Configure the notification channel.
            notificationChannel.setDescription("Description");

            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{300, 300});

            notificationManager.createNotificationChannel(notificationChannel);
            builder.setOngoing(true)
                    .setChannelId(channelID);
        }
        notificationManager.notify(id, builder.build());
        Log.i("TAG", "Service created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("TAG", "Service on start command");
        new Thread(() -> {
            Log.i("TAG", "Service thread on start command");
            AddPostDTO postDTO = intent.getParcelableExtra(KeyTransferObj.POST.toString());
            Uri postImageUri = intent.getParcelableExtra(KeyTransferObj.AVATAR_URI.toString());
            uris = new ArrayList<>();

            // Сжать все картинки и видео

            // По ури создать bitmap
            // Resize it
            // Save it
            // Get new uri
            try {
                //Avatar
                uris.add(BitmapUtil.saveBitmap(BitmapUtil.resizeBitmap(
                        MediaStore.Images.Media.getBitmap(
                                getContentResolver(),
                                postImageUri)),
                        AddPostService.this, "temp", "postImage.png"));

                for (int i = 0; i < postDTO.getElements().size(); i++) {
                    if (postDTO.getElements().get(i).getType().equals("image")){
                        resizeAndSaveImages(postDTO.getElements().get(i).getUris(), i);
                    }
                    else if(postDTO.getElements().get(i).getType().equals("video")){
                        resizeAndSaveVideos(postDTO.getElements().get(i).getUris(), i);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("TAG", "Done with resizing");
            for(int i =0;i<postDTO.getElements().size();i++)
                postDTO.getElements().get(i).setUris(null);
            new Connection<SenderContainerDTO>().addPost(AddPostService.this, postDTO,
                    uris, AddPostService.this, (isSuccess, answerServerResponse) -> {
                        if (isSuccess) {
                            onFinish();
                        } else {
                            // TODO handle error
                        }
                    });

        }).start();
        return super.onStartCommand(intent, flags, startId);
    }


    private void resizeAndSaveVideos(List<Uri> newUris, int position) {
        for(int i =0;i<newUris.size();i++){
            uris.add(VideoUtil.saveVideo(this, newUris.get(i), position + "video" + i, "temp"));
        }
    }

    private void resizeAndSaveImages(List<Uri> newUris, int position) {
        for(int i = 0; i < newUris.size(); i++){
            try {
                uris.add(
                BitmapUtil.saveBitmap(BitmapUtil.resizeBitmap(MediaStore.Images.Media.getBitmap(
                        getContentResolver(),
                        newUris.get(i))),
                        this, "temp",  position + "image"+ i + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("TAG", "Service destroyed");
    }

    @Override
    public void onProgressUpdate(int percentage) {
        Log.i("TAG", "Upload percentage" + percentage);
        builder.setProgress(100, percentage, false);
        builder.setContentText(String.valueOf(percentage) + "%");
        notificationManager.notify(id, builder.build());
    }

    @Override
    public void onError() {
        Log.i("TAG", "upload error");
        builder.setContentText("Upload error")
                .setProgress(0,0,false)
                .setOngoing(false);
        notificationManager.notify(id, builder.build());
    }

    @Override
    public void onFinish() {
        Log.i("TAG", "upload complete");
        builder.setContentText("Upload complete")
                .setProgress(0,0,false)
                .setOngoing(false);
        notificationManager.notify(id, builder.build());
        onDestroy();
    }
}
