package com.jqh.generalec.example.genterators;


/**
 * Created by 傅令杰 on 2017/4/22
 */

import com.example.annotations.AppRegisterGenerator;
import com.jqh.jqh.wechat.templates.AppRegisterTemplate;

@SuppressWarnings("unused")
@AppRegisterGenerator(
        packageName = "com.diabin.fastec.example",
        registerTemplate = AppRegisterTemplate.class
)
public interface AppRegister {
}
