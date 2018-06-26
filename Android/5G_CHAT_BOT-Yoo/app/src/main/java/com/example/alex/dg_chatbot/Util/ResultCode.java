package com.example.alex.dg_chatbot.Util;

/**
 * Created by alex on 2018. 5. 10..
 */

public class ResultCode {
    private final String NOTICE_RESULT = "920625";
    private final String SCHEDULE_RESULT = "560901";
    private final String LECTURE_RESULT = "581016";
    private final String ETC_RESULT = "890719";
    private final String LOGIN_ACTIVITY = "123000";
    private final String SETTINGS_FRAGMENT = "123001";

    public String getLOGIN_ACTIVITY() {
        return LOGIN_ACTIVITY;
    }

    public String getSETTINGS_FRAGMENT() {
        return SETTINGS_FRAGMENT;
    }

    public String getNOTICE_RESULT() {
        return NOTICE_RESULT;
    }

    public String getSCHEDULE_RESULT() {
        return SCHEDULE_RESULT;
    }

    public String getLECTURE_RESULT() {
        return LECTURE_RESULT;
    }

    public String getETC_RESULT() {
        return ETC_RESULT;
    }
}
