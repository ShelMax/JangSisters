package kr.sofac.jangsisters.network;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import kr.sofac.jangsisters.models.Category;
import kr.sofac.jangsisters.models.Comment;
import kr.sofac.jangsisters.models.Ingredient;
import kr.sofac.jangsisters.models.Post;
import kr.sofac.jangsisters.models.User;
import kr.sofac.jangsisters.models.Version;
import kr.sofac.jangsisters.network.api.ManagerRetrofit;
import kr.sofac.jangsisters.network.api.type.ServerResponse;
import kr.sofac.jangsisters.network.dto.AddPostDTO;
import kr.sofac.jangsisters.network.dto.SenderContainerDTO;
import kr.sofac.jangsisters.utils.PathUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Connection<T> {

    private AnswerServerResponse<T> answerServerResponse;

    public interface AnswerServerResponse<T> {
        void processFinish(Boolean isSuccess, ServerResponse<T> answerServerResponse);
    }

    public void signInCustomer(SenderContainerDTO senderContainerDTO, AnswerServerResponse<T> async) { //Change name request / Change data in method parameters
        answerServerResponse = async;
        new ManagerRetrofit<SenderContainerDTO>().sendRequest(senderContainerDTO, new Object() {// Change type Object sending / Change data sending
        }.getClass().getEnclosingMethod().getName(), (isSuccess, answerString) -> {
            if (isSuccess) {
                Type typeAnswer = new TypeToken<ServerResponse<User>>() { //Change type response
                }.getType();
                tryParsing(answerString, typeAnswer);
            } else {
                answerServerResponse.processFinish(false, null);
            }
        });
    }

    public void getIngredients(AnswerServerResponse<T> async) {
        answerServerResponse = async;
        new ManagerRetrofit<String>().sendRequest("", "getListShopIngredients", (isSuccess, answerString) -> {
            if (isSuccess) {
                Type typeAnswer = new TypeToken<ServerResponse<List<Ingredient>>>() {
                }.getType();
                tryParsing(answerString, typeAnswer);
            } else {
                answerServerResponse.processFinish(false, null);
            }
        });
    }




    public void signUpCustomer(SenderContainerDTO senderContainerDTO, AnswerServerResponse<T> async) { //Change name request / Change data in method parameters
        answerServerResponse = async;
        new ManagerRetrofit<SenderContainerDTO>().sendRequest(senderContainerDTO, new Object() {// Change type Object sending / Change data sending
        }.getClass().getEnclosingMethod().getName(), (isSuccess, answerString) -> {
            if (isSuccess) {
                Type typeAnswer = new TypeToken<ServerResponse<User>>() { //Change type response
                }.getType();
                tryParsing(answerString, typeAnswer);
            } else {
                answerServerResponse.processFinish(false, null);
            }
        });
    }

    public void signUpCustomerVerification(SenderContainerDTO senderContainerDTO, AnswerServerResponse<T> async) { //Change name request / Change data in method parameters
        answerServerResponse = async;
        new ManagerRetrofit<SenderContainerDTO>().sendRequest(senderContainerDTO, new Object() {// Change type Object sending / Change data sending
        }.getClass().getEnclosingMethod().getName(), (isSuccess, answerString) -> {
            if (isSuccess) {
                Type typeAnswer = new TypeToken<ServerResponse>() { //Change type response
                }.getType();
                tryParsing(answerString, typeAnswer);
            } else {
                answerServerResponse.processFinish(false, null);
            }
        });
    }

    public void getPost(SenderContainerDTO senderContainerDTO, AnswerServerResponse<T> async) { //Change name request / Change data in method parameters
        answerServerResponse = async;
        new ManagerRetrofit<SenderContainerDTO>().sendRequest(senderContainerDTO, new Object() {// Change type Object sending / Change data sending
        }.getClass().getEnclosingMethod().getName(), (isSuccess, answerString) -> {
            if (isSuccess) {
                Type typeAnswer = new TypeToken<ServerResponse<Post>>() { //Change type response
                }.getType();
                tryParsing(answerString, typeAnswer);
            } else {
                answerServerResponse.processFinish(false, null);
            }
        });
    }


    public void signUpCustomerResendVerification(Integer userID, AnswerServerResponse<T> async) { //Change name request / Change data in method parameters
        answerServerResponse = async;
        new ManagerRetrofit<Integer>().sendRequest(userID, new Object() {// Change type Object sending / Change data sending
        }.getClass().getEnclosingMethod().getName(), (isSuccess, answerString) -> {
            if (isSuccess) {
                Type typeAnswer = new TypeToken<ServerResponse>() { //Change type response
                }.getType();
                tryParsing(answerString, typeAnswer);
            } else {
                answerServerResponse.processFinish(false, null);
            }
        });
    }

    public void getListPosts(SenderContainerDTO senderContainerDTO, AnswerServerResponse<T> async) { //Change name request / Change data in method parameters
        answerServerResponse = async;
        new ManagerRetrofit<SenderContainerDTO>().sendRequest(senderContainerDTO,
                "getListPosts", (isSuccess, answerString) -> {
            if (isSuccess) {
                Type typeAnswer = new TypeToken<ServerResponse<List<Post>>>() {
                }.getType();
                tryParsing(answerString, typeAnswer);
            } else {
                answerServerResponse.processFinish(false, null);
            }
        });
    }

    public void getListCategories(String emptyString, AnswerServerResponse<T> async) { //Change name request / Change data in method parameters
        answerServerResponse = async;
        new ManagerRetrofit<String>().sendRequest(emptyString, new Object() {// Change type Object sending / Change data sending
        }.getClass().getEnclosingMethod().getName(), (isSuccess, answerString) -> {
            if (isSuccess) {
                Type typeAnswer = new TypeToken<ServerResponse<List<Category>>>() { //Change type response
                }.getType();
                tryParsing(answerString, typeAnswer);
            } else {
                answerServerResponse.processFinish(false, null);
            }
        });
    }

    public void addCommentToPost(SenderContainerDTO senderContainerDTO, AnswerServerResponse<T> async) { //Change name request / Change data in method parameters
        answerServerResponse = async;
        new ManagerRetrofit<SenderContainerDTO>().sendRequest(senderContainerDTO, "addComment",
                (isSuccess, answerString) -> {
            if (isSuccess) {
                Type typeAnswer = new TypeToken<ServerResponse<Comment>>() { //Change type response
                }.getType();
                tryParsing(answerString, typeAnswer);
            } else {
                answerServerResponse.processFinish(false, null);
            }
        });
    }

    public void getPostComments(int postID, AnswerServerResponse<T> async) { //Change name request / Change data in method parameters
        answerServerResponse = async;
        new ManagerRetrofit<Integer>().sendRequest(postID, "getPostListComments",
                (isSuccess, answerString) -> {
                    if (isSuccess) {
                        Type typeAnswer = new TypeToken<ServerResponse<List<Comment>>>() { //Change type response
                        }.getType();
                        tryParsing(answerString, typeAnswer);
                    } else {
                        answerServerResponse.processFinish(false, null);
                    }
                });
    }

    public void getFollowers(Integer userID, AnswerServerResponse<T> async){
        answerServerResponse = async;
        new ManagerRetrofit<Integer>().sendRequest(userID, "getListSubscribers",
                ((isSuccess, answerString) -> {
                    if(isSuccess){
                        Type typeAnswer = new TypeToken<ServerResponse<List<User>>>(){
                        }.getType();
                        tryParsing(answerString, typeAnswer);
                    }
                    else{
                        answerServerResponse.processFinish(false, null);
                    }
                })
        );
    }

    public void getUserPosts(SenderContainerDTO senderContainerDTO, AnswerServerResponse<T> async) {
        answerServerResponse = async;
        new ManagerRetrofit<SenderContainerDTO>().sendRequest(senderContainerDTO, "getUserListPosts",
                ((isSuccess, answerString) -> {
                    if (isSuccess) {
                        Type typeAnswer = new TypeToken<ServerResponse<List<Post>>>() {
                        }.getType();
                        tryParsing(answerString, typeAnswer);
                    } else {
                        answerServerResponse.processFinish(false, null);
                    }
                })
        );
    }

    public void getUserBookmarks(Integer userID, AnswerServerResponse<T> async) {
        answerServerResponse = async;
        new ManagerRetrofit<Integer>().sendRequest(userID, "getUserBookmarkListPosts",
                ((isSuccess, answerString) -> {
                    if (isSuccess) {
                        Type typeAnswer = new TypeToken<ServerResponse<List<Post>>>() {
                        }.getType();
                        tryParsing(answerString, typeAnswer);
                    } else {
                        answerServerResponse.processFinish(false, null);
                    }
                })
        );
    }

    public void getFollowing(Integer userID, AnswerServerResponse<T> async){
        answerServerResponse = async;
        new ManagerRetrofit<Integer>().sendRequest(userID, "getListSubscriptions",
                ((isSuccess, answerString) -> {
                    if(isSuccess){
                        Type typeAnswer = new TypeToken<ServerResponse<List<User>>>(){
                        }.getType();
                        tryParsing(answerString, typeAnswer);
                    }
                    else{
                        answerServerResponse.processFinish(false, null);
                    }
                })
        );
    }

    public void getUserByID(SenderContainerDTO senderContainerDTO, AnswerServerResponse<T> async) {
        answerServerResponse = async;
        new ManagerRetrofit<SenderContainerDTO>().sendRequest(senderContainerDTO, "getUser",
                ((isSuccess, answerString) -> {
                    if(isSuccess){
                        Type typeAnswer = new TypeToken<ServerResponse<User>>(){
                        }.getType();
                        tryParsing(answerString, typeAnswer);
                    }
                    else{
                        answerServerResponse.processFinish(false, null);
                    }
                })
        );
    }

    public void likePost(SenderContainerDTO senderContainerDTO, AnswerServerResponse<T> async){
        answerServerResponse = async;
        new ManagerRetrofit<SenderContainerDTO>().sendRequest(senderContainerDTO, "addLike",
                ((isSuccess, answerString) -> {
                    if(isSuccess){
                        Type typeAnswer = new TypeToken<ServerResponse>(){
                        }.getType();
                        tryParsing(answerString, typeAnswer);
                    }
                    else{
                        answerServerResponse.processFinish(false, null);
                    }
                })
        );
    }

    public void addPostToBookmarks(SenderContainerDTO senderContainerDTO, AnswerServerResponse<T> async) {
        answerServerResponse = async;
        new ManagerRetrofit<SenderContainerDTO>().sendRequest(senderContainerDTO, "addBookmark",
                ((isSuccess, answerString) -> {
                    if (isSuccess) {
                        Type typeAnswer = new TypeToken<ServerResponse>() {
                        }.getType();
                        tryParsing(answerString, typeAnswer);
                    } else {
                        answerServerResponse.processFinish(false, null);
                    }
                })
        );
    }

    public void getPostListComments(Integer postID, AnswerServerResponse<T> async) { //Change name request / Change data in method parameters
        answerServerResponse = async;
        new ManagerRetrofit<Integer>().sendRequest(postID, new Object() {// Change type Object sending / Change data sending
        }.getClass().getEnclosingMethod().getName(), (isSuccess, answerString) -> {
            if (isSuccess) {
                Type typeAnswer = new TypeToken<ServerResponse>() { //Change type response
                }.getType();
                tryParsing(answerString, typeAnswer);
            } else {
                answerServerResponse.processFinish(false, null);
            }
        });
    }

    public void getCorrectVersion(String empty, AnswerServerResponse<T> async) { //Change name request / Change data in method parameters
        answerServerResponse = async;
        new ManagerRetrofit<String>().sendRequest(empty, new Object() {// Change type Object sending / Change data sending
        }.getClass().getEnclosingMethod().getName(), (isSuccess, answerString) -> {
            if (isSuccess) {
                Type typeAnswer = new TypeToken<ServerResponse<Version>>() { //Change type response
                }.getType();
                tryParsing(answerString, typeAnswer);
            } else {
                answerServerResponse.processFinish(false, null);
            }
        });
    }

    public void addPost(Context context, AddPostDTO postDTO, List<Uri> listUri, AnswerServerResponse<T> async) {
        answerServerResponse = async;
        new ManagerRetrofit<AddPostDTO>().sendMultiPartRequest(postDTO, "addPost",
                generateMultiPartList(listUri, context), (isSuccess, answerString) -> {
                    if (isSuccess) {
                        Type typeAnswer = new TypeToken<ServerResponse>() { //Change type response(тип ответа)
                        }.getType();
                        tryParsing(answerString, typeAnswer);
                    } else {
                        answerServerResponse.processFinish(false, null);
                    }
                });
    }

    public void updateUser(Context context, SenderContainerDTO senderContainerDTO, Uri imageUri, AnswerServerResponse<T> async) { //Change name request / Change data in method parameters
        answerServerResponse = async;
        new ManagerRetrofit<SenderContainerDTO>().sendMultiPartRequest(senderContainerDTO, "updateUserInfo",
                generateMultiPartList(imageUri, context),
                (isSuccess, answerString) -> {
                    if (isSuccess) {
                        Type typeAnswer = new TypeToken<ServerResponse<User>>() {
                        }.getType();
                        tryParsing(answerString, typeAnswer);
                    } else {
                        answerServerResponse.processFinish(false, null);
                    }
                });
    }

    public void updateUser(SenderContainerDTO senderContainerDTO, AnswerServerResponse<T> async) { //Change name request / Change data in method parameters
        answerServerResponse = async;
        new ManagerRetrofit<SenderContainerDTO>().sendRequest(senderContainerDTO, "updateUserInfo",
                (isSuccess, answerString) -> {
                    if (isSuccess) {
                        Type typeAnswer = new TypeToken<ServerResponse<User>>() {
                        }.getType();
                        tryParsing(answerString, typeAnswer);
                    } else {
                        answerServerResponse.processFinish(false, null);
                    }
                });
    }

//    public void updatePost(Context context, PostDTO postDTO, ArrayList<Uri> listUri, ArrayList<String> listDeletingFiles, AnswerServerResponse<T> async) {
//        answerServerResponse = async;
//        new ManagerRetrofit<PostDTO>().sendMultiPartWithTwoObj(postDTO, new Object() {// Change (type sending) / (data sending)
//        }.getClass().getEnclosingMethod().getTitle(), generateMultiPartList(listUri, context), listDeletingFiles, (isSuccess, answerString) -> {
//            if (isSuccess) {
//                Type typeAnswer = new TypeToken<ServerResponse>() { //Change type response(тип ответа)
//                }.getType();
//                tryParsing(answerString, typeAnswer);
//            } else {
//                answerServerResponse.processFinish(false, null);
//            }
//        });
//
//    }
//


    /**
     * Supporting methods
     */

    private List<MultipartBody.Part> generateMultiPartList(List<Uri> listFileUri, Context context) {
        List<MultipartBody.Part> arrayListMulti = new ArrayList<>();
        for (int i = 0; i < listFileUri.size(); i++) {
            File file = new File(PathUtil.getPath(context, listFileUri.get(i)));
            arrayListMulti.add(MultipartBody.Part.createFormData("files[" + i + "]", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file)));
        }
        return arrayListMulti;
    }

    private List<MultipartBody.Part> generateMultiPartList(Uri imageUri, Context context) {
        List<MultipartBody.Part> arrayListMulti = new ArrayList<>();
        File file = new File(PathUtil.getPath(context, imageUri));
        arrayListMulti.add(MultipartBody.Part.createFormData("files[0]", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file)));
        return arrayListMulti;
    }

    private void tryParsing(String answerString, Type typeAnswer) {
        try {
            answerServerResponse.processFinish(true, getObjectTransferFromJSON(answerString, typeAnswer));
        } catch (Exception e) {
            answerServerResponse.processFinish(false, null);
            e.printStackTrace();
        }
    }

    private ServerResponse<T> getObjectTransferFromJSON(String string, Type type) {
        try {
            return new Gson().fromJson(string, type);
        } catch (JsonSyntaxException e) {

            Log.e("ERROR_PARSING_JSON","Не соответствующий тип данных для парсинга JSON");
            e.printStackTrace();
            return null;
        }
    }

}
