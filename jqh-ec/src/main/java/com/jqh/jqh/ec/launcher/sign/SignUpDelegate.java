package com.jqh.jqh.ec.launcher.sign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.ec.R;
import com.jqh.jqh.net.RestClient;
import com.jqh.jqh.net.calback.ISuccess;

/**
 * Created by jiangqianghua on 18/3/8.
 */

public class SignUpDelegate extends JqhDelegate {

    private TextInputEditText mName ;
    private TextInputEditText mEmail ;
    private TextInputEditText mPhone;
    private TextInputEditText mPassword;
    private TextInputEditText mRePassword;
    private Button mSignBtn ;
    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mName = (TextInputEditText)rootView.findViewById(R.id.edit_sign_name);
        mEmail = (TextInputEditText)rootView.findViewById(R.id.edit_sign_mail);
        mPhone = (TextInputEditText)rootView.findViewById(R.id.edit_sign_phone);
        mPassword = (TextInputEditText)rootView.findViewById(R.id.edit_sign_password);
        mRePassword = (TextInputEditText)rootView.findViewById(R.id.edit_sign_re_password);
        mSignBtn = (Button)rootView.findViewById(R.id.btn_sign_up);
        initEvent();
    }

    private void initEvent(){
        mSignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkForm()){
//                    RestClient.builder().url("sign_up")
//                            .params("","")
//                            .success(new ISuccess() {
//                                @Override
//                                public void onSuccess(String response) {
//
//                                }
//                            }).build()
//                            .post();
                    Toast.makeText(getContext(),"验证通过",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkForm(){
        final String name = mName.getText().toString();
        final String email = mEmail.getText().toString();
        final String phone = mPhone.getText().toString();
        final String password = mPassword.getText().toString();
        final String rePassword = mRePassword.getText().toString();
        boolean isPass = true;
        if(name.isEmpty()){
            mName.setError("请输入名称");
            isPass = false ;
        }else{
            mName.setError(null);
        }

        if(email.isEmpty() ||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("错误的邮箱");
            isPass = false ;
        }else{
            mEmail.setError(null);
        }

        if(phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            mPhone.setError("错误的号码");
            isPass = false ;
        }else{
            mPhone.setError(null);
        }

        if(password.isEmpty() || password.length()<6){
            mPassword.setError("请输入至少6位的密码");
            isPass = false ;
        }else{
            mPassword.setError(null);
        }


        if(rePassword.isEmpty() || rePassword.length() < 6 || (!rePassword.equals(password))){
            mRePassword.setError("密码验证错误");
            isPass = false ;
        }
        else{
            mRePassword.setError(null);
        }
        return isPass;
    }
}
