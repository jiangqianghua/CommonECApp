package com.jqh.jqh.ec.main.personal.profile;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.ec.R;
import com.jqh.jqh.ec.main.personal.list.ListBean;
import com.jqh.jqh.net.RestClient;
import com.jqh.jqh.net.calback.ISuccess;
import com.jqh.jqh.ui.date.DateDialogUtil;
import com.jqh.jqh.utils.callback.CallbackManager;
import com.jqh.jqh.utils.callback.CallbackType;
import com.jqh.jqh.utils.callback.IGlobalCallback;
import com.jqh.jqh.utils.log.JqhLogger;

public class UserProfileClickListener extends SimpleClickListener {

    private final UserProfileDelegate DELEGATE;

    private String[] mGenders = new String[]{"男", "女", "保密"};


    public UserProfileClickListener(UserProfileDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter,final View view, int position) {
        final ListBean bean = (ListBean)baseQuickAdapter.getData().get(position);
        final int id = bean.getId();
        switch (id){
            case 1:
                // 照相机或则选择图片
                CallbackManager.getInstance()
                        .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                            @Override
                            public void executeCallback(@Nullable Uri args) {
                                // 裁剪回掉接口
                                JqhLogger.d("executeCallback",args);
                                final ImageView avater = (ImageView)view.findViewById(R.id.img_arrow_avatar);
                                Glide.with(DELEGATE)
                                        .load(args)
                                        .into(avater);
                                RestClient.builder()
                                        .url("")
                                        .file(args.getPath())
                                        .success(new ISuccess() {
                                            @Override
                                            public void onSuccess(String response) {
                                                //  上传，获取url连接
                                            }
                                        })
                                        .build().post();
                            }
                        });
                DELEGATE.startCameraWithCheck();
                break;
            case 2:
                // 名称选择
                final JqhDelegate nameDelegate = bean.getDelegate();
                DELEGATE.getSupportDelegate().start(nameDelegate);
                break;
            case 3:
                getGenderDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final TextView textView = (TextView) view.findViewById(R.id.tv_arrow_value);
                        textView.setText(mGenders[which]);
                        dialog.cancel();
                    }
                });
                break;
            case 4:
                final DateDialogUtil dateDialogUtil = new DateDialogUtil();
                dateDialogUtil.setDateListener(new DateDialogUtil.IDateListener() {
                    @Override
                    public void onDateChange(String date) {
                        final TextView textView = (TextView) view.findViewById(R.id.tv_arrow_value);
                        textView.setText(date);
                    }
                });
                dateDialogUtil.showDialog(DELEGATE.getContext());
                break;
        }
    }

    private void getGenderDialog(DialogInterface.OnClickListener listener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DELEGATE.getContext());
        builder.setSingleChoiceItems(mGenders, 0, listener);
        builder.show();
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }


}
