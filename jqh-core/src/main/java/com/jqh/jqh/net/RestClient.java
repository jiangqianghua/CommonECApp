package com.jqh.jqh.net;

import android.content.Context;

import com.jqh.jqh.net.calback.IError;
import com.jqh.jqh.net.calback.IFailure;
import com.jqh.jqh.net.calback.IRequest;
import com.jqh.jqh.net.calback.ISuccess;
import com.jqh.jqh.net.calback.RequestCallBacks;
import com.jqh.jqh.net.download.DownloadHandler;
import com.jqh.jqh.ui.JqhLoader;
import com.jqh.jqh.ui.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by jiangqianghua on 18/2/19.
 */

public class RestClient {

    private final String URL ;

    private final WeakHashMap<String,Object> PARAMS = RestCreator.getParams() ;

    private final IRequest REQUEST ;

    private final ISuccess SUCCESS ;

    private final IError ERROR ;

    private final IFailure FAILURE ;

    private final RequestBody BODY ;

    private final LoaderStyle LOADER_STYLE ;

    private final Context CONTEXT;

    private final File FILE ;

    //  download
    private final String DOWNLOAD_DIR ;

    private final String EXTENSION ;

    private final String NAME;



    public RestClient(String URL,
                      Map<String, Object> PARAMS,
                      IRequest REQUEST,
                      ISuccess SUCCESS,
                      IError ERROR,
                      IFailure FAILURE,
                      RequestBody BODY,
                      Context context,
                      LoaderStyle loaderStyle,
                      File file,
                      String download_dir,
                      String extension,
                      String name) {
        this.URL = URL;
        this.PARAMS.putAll(PARAMS);
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
        this.ERROR = ERROR;
        this.FAILURE = FAILURE;
        this.BODY = BODY;
        this.CONTEXT = context ;
        this.LOADER_STYLE = loaderStyle ;
        this.FILE = file ;
        this.DOWNLOAD_DIR = download_dir ;
        this.EXTENSION = extension ;
        this.NAME = name ;
    }

    public static RestClientBuilder builder(){
        return new RestClientBuilder();
    }

    private void request(HttpMethod method){
        final RestService service = RestCreator.getRestService();
        Call<String> call = null ;
        if(REQUEST != null){
            REQUEST.onRequestStart();
        }
        if(LOADER_STYLE != null){
            JqhLoader.showLoading(CONTEXT,LOADER_STYLE);
        }
        switch (method){
            case GET:
                call = service.get(URL,PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL,BODY);
                break;
            case POST:
                call = service.post(URL,PARAMS);
                break;
            case DELETE:
                call = service.delete(URL,PARAMS);
                break;
            case PUT:
                call = service.put(URL,PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL,BODY);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                call = RestCreator.getRestService().upload(URL,body);
                break;
        }
        if(call != null){
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback(){
        return new RequestCallBacks(REQUEST,SUCCESS,ERROR,FAILURE,LOADER_STYLE);
    }

    public final void get(){
        request(HttpMethod.GET);
    }

    public final void post(){
        if(BODY == null)
            request(HttpMethod.POST);
        else{
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("Params must be null");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put(){
        if(BODY == null)
            request(HttpMethod.PUT);
        else{
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("Params must be null");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete(){
        request(HttpMethod.DELETE);
    }

    public final void upload(){
        request(HttpMethod.UPLOAD);
    }

    public final void download(){
        new DownloadHandler(URL,REQUEST,SUCCESS,ERROR,FAILURE,DOWNLOAD_DIR,EXTENSION,NAME).handlerDownload();
    }
}
