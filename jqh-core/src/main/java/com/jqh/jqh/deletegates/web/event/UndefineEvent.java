package com.jqh.jqh.deletegates.web.event;

import com.jqh.jqh.utils.log.JqhLogger;

public class UndefineEvent extends Event {
    @Override
    public String execute(String params) {
        JqhLogger.e("undefineEvent",params);
        return null;
    }
}
