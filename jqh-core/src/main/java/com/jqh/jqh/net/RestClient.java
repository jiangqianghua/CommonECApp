package com.jqh.jqh.net;

import android.content.Context;

import com.jqh.jqh.net.calback.IError;
import com.jqh.jqh.net.calback.IFailure;
import com.jqh.jqh.net.calback.IRequest;
import com.jqh.jqh.net.calback.ISuccess;
import com.jqh.jqh.net.calback.RequestCallBacks;
import com.jqh.jqh.ui.JqhLoader;
import com.jqh.jqh.ui.LoaderStyle;

import java.util.Map;
import java.util.WeakHashMap;

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


    public RestClient(String URL,
                      Map<String, Object> PARAMS,
                      IRequest REQUEST,
                      ISuccess SUCCESS,
                      IError ERROR,
                      IFailure FAILURE,
                      RequestBody BODY,
                      Context context,
                      LoaderStyle loaderStyle) {
        this.URL = URL;
        this.PARAMS.putAll(PARAMS);
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
        this.ERROR = ERROR;
        this.FAILURE = FAILURE;
        this.BODY = BODY;
        this.CONTEXT = context ;
        this.LOADER_STYLE = loaderStyle ;
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
            case POST:
                call = service.post(URL,PARAMS);
                break;
            case DELETE:
                call = service.delete(URL,PARAMS);
                break;
            case PUT:
                call = service.put(URL,PARAMS);
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
        request(HttpMethod.POST);
    }

    public final void put(){
        request(HttpMethod.PUT);
    }

    public final void delete(){
        request(HttpMethod.DELETE);
    }

}
