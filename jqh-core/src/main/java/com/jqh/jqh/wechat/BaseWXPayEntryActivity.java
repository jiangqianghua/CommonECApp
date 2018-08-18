package com.jqh.jqh.wechat;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;

public abstract class BaseWXPayEntryActivity extends BaseWXActivity {

    private static final  int WX_PAY_SUCCESS = 0 ;
    private static final int WX_PAY_FAIL = -1 ;
    private static  final int WX_PAY_CANCEL = -2 ;

    protected abstract  void onPaySuccess();

    protected abstract  void onPayFailt();

    protected abstract void onPayCalcel();

    @Override
    public void onResp(BaseResp baseResp) {
        if(baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX){
            switch (baseResp.errCode){
                case WX_PAY_SUCCESS:
                    onPaySuccess();
                    break;
                case WX_PAY_FAIL:
                    onPayFailt();
                    break;
                case WX_PAY_CANCEL:
                    onPayCalcel();
                    break;
            }
        }
    }
}
