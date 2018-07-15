package com.jqh.jqh.ec.launcher.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.ec.R;
import com.jqh.jqh.net.RestClient;
import com.jqh.jqh.net.calback.ISuccess;

/**
 * Created by jiangqianghua on 18/3/15.
 */

public class SignInDelegate extends JqhDelegate {

    private TextInputEditText mEmail ;
    private TextInputEditText mPassword;
    private AppCompatButton mSignInBtn;
    private AppCompatTextView mlinkSignUp;
    private ISignListener mISignListener ;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ISignListener){
            mISignListener = (ISignListener)activity;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mEmail = (TextInputEditText) rootView.findViewById(R.id.edit_sign_in_mail);
        mPassword = (TextInputEditText)rootView.findViewById(R.id.edit_sign_in_password);
        mSignInBtn = (AppCompatButton)rootView.findViewById(R.id.btn_sign_in);
        mlinkSignUp = (AppCompatTextView)rootView.findViewById(R.id.tv_link_sign_up);
        initEvent();
    }

    private void initEvent(){
        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkForm()){
                    RestClient.builder().url("http://127.0.0.1/index")
                            .params("email",mEmail.getText().toString())
                            .params("password",mPassword.getText().toString())
                            .success(new ISuccess() {
                                @Override
                                public void onSuccess(String response) {
                                    SignHandler.signIn(response,null);
                                }
                            }).build()
                            .post();
                }
            }
        });

        mlinkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(new SignUpDelegate());
            }
        });
    }

    private boolean checkForm(){
        boolean isPass = true ;
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        if(email.isEmpty()){
            mEmail.setError("请输入邮箱账户");
            isPass = false;
        }else{
            mEmail.setError(null);
        }
        if(password.isEmpty()){
            mPassword.setError("请输入密码");
            isPass = false;
        }else{
            mPassword.setError(null);
        }
        return isPass;
    }
}
