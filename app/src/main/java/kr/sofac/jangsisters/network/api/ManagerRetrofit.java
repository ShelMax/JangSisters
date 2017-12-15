package kr.sofac.jangsisters.network.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import kr.sofac.jangsisters.network.api.type.ServerRequest;
import kr.sofac.jangsisters.network.api.type.ServerResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static kr.sofac.jangsisters.config.Server.BASE_URL;

/**
 * Created by Maxim on 03.08.2017.
 */

public class ManagerRetrofit<T> {

    private String serverResponseError = "SERVER_RESPONSE_ERROR";
    private String serverResponseSuccess = "SERVER_RESPONSE_SUCCESS";
    private String serverResponse = serverResponseError;
    private String baseUrl = BASE_URL;

    private ServiceRetrofit serviceRetrofit;
    private ServerRequest serverRequest;

    private AsyncAnswerString answerString = null;


    {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        serviceRetrofit = retrofit.create(ServiceRetrofit.class);
    }

    public interface AsyncAnswerString {
        void processFinish(Boolean isSuccess, String answerString);
    }

    @SuppressWarnings("unchecked")
    private void sendRequest(T object, String requestType, Callback<ResponseBody> responseBodyCallback) {
        serverRequest = new ServerRequest(requestType, object);
        logServerRequest(serverRequest);
        serviceRetrofit.getData(serverRequest).enqueue(responseBodyCallback);
    }

    public void sendRequest(T object, String requestType, AsyncAnswerString asyncAnswer) {
        answerString = asyncAnswer;

        sendRequest(object, requestType, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    serverResponse = logServerResponse(response.body().string());
                    if (serverResponseSuccess.equals(getServerResponseStringFromJSON(serverResponse).getResponseStatus())) {
                        answerString.processFinish(true, serverResponse);
                    } else {
                        answerString.processFinish(false, null);
                    }
                } catch (IOException e) {
                    answerString.processFinish(false, null);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                answerString.processFinish(false, null);
                t.printStackTrace();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public void sendMultiPartRequest(T object, String requestType, ArrayList<MultipartBody.Part> partArrayList, AsyncAnswerString asyncAnswer) {
        serverRequest = new ServerRequest(requestType, object);
        answerString = asyncAnswer;
        logServerRequest(serverRequest);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Call<ResponseBody> call = serviceRetrofit.sendMultiPartRequest(
                RequestBody.create(
                        MediaType.parse("text/plain"),
                        gson.toJson(serverRequest)),
                partArrayList);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    serverResponse = logServerResponse(response.body().string());
                    if (serverResponseSuccess.equals(getServerResponseStringFromJSON(serverResponse).getResponseStatus())) {
                        answerString.processFinish(true, serverResponse);
                    } else {
                        answerString.processFinish(false, null);
                    }
                } catch (IOException e) {
                    answerString.processFinish(false, null);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                answerString.processFinish(false, null);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public void sendMultiPartWithTwoObj(T object, String requestType, ArrayList<MultipartBody.Part> partArrayList, ArrayList<String> listDeletingFiles, AsyncAnswerString asyncAnswer) {
        serverRequest = new ServerRequest(requestType, object);
        answerString = asyncAnswer;
        logServerRequest(serverRequest);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        String stringDeleting = "";
        for (String str : listDeletingFiles) {
            stringDeleting = stringDeleting + ";" + str;
        }

        Call<ResponseBody> call = serviceRetrofit.sendMultiPartWithTwoObj(
                RequestBody.create(
                        MediaType.parse("text/plain"),
                        gson.toJson(serverRequest)),
                RequestBody.create(
                        MediaType.parse("text/plain"),
                        stringDeleting),
                partArrayList);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    serverResponse = logServerResponse(response.body().string());
                    if (serverResponseSuccess.equals(getServerResponseStringFromJSON(serverResponse).getResponseStatus())) {
                        answerString.processFinish(true, serverResponse);
                    } else {
                        answerString.processFinish(false, null);
                    }
                } catch (IOException e) {
                    answerString.processFinish(false, null);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                answerString.processFinish(false, null);
            }
        });
    }



    /***/
    private ServerResponse<String> getServerResponseStringFromJSON(String stringJSON) {
        Type typeServerResponse = new TypeToken<ServerResponse>() {
        }.getType();
        try {
            return new Gson().fromJson(stringJSON, typeServerResponse);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return new ServerResponse<>(serverResponseError, "");
    }

    private void logServerRequest(ServerRequest serverRequest) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Log.e("SERVER_REQUEST", "\n\n>>>>>>>>>>>>>>>>> \n" + gson.toJson(serverRequest));
    }

    /**
     * Логирование данных приема
     */
    private String logServerResponse(String serverResponse) {
        Log.e("SERVER_RESPONSE", "\n\n<<<<<<<<<<<<<<<< \n" + serverResponse);
        return serverResponse;
    }

}
