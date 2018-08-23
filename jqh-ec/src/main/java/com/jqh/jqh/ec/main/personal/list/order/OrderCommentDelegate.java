package com.jqh.jqh.ec.main.personal.list.order;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.ec.R;
import com.jqh.jqh.utils.callback.CallbackManager;
import com.jqh.jqh.utils.callback.CallbackType;
import com.jqh.jqh.utils.callback.IGlobalCallback;
import com.jqh.jqh.widget.AutoPhotoLayout;
import com.jqh.jqh.widget.StarLayout;

public class OrderCommentDelegate extends JqhDelegate{

    StarLayout mStarLayout = null;
    AppCompatTextView mSubmit = null ;
    AutoPhotoLayout mAutoPhotoLayout = null;
    @Override
    public Object setLayout() {
        return R.layout.delegate_order_comment;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mStarLayout = (StarLayout) rootView.findViewById(R.id.custom_star_layout);
        mSubmit = (AppCompatTextView)rootView.findViewById(R.id.top_tv_comment_commit);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "评分： " + mStarLayout.getStarCount(), Toast.LENGTH_LONG).show();
            }
        });

         mAutoPhotoLayout = (AutoPhotoLayout)rootView.findViewById(R.id.custom_auto_photo_layout);

        mAutoPhotoLayout.setDelegate(this);
        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                    @Override
                    public void executeCallback(@Nullable Uri args) {
                        mAutoPhotoLayout.onCropTarget(args);
                    }
                });
    }
}
