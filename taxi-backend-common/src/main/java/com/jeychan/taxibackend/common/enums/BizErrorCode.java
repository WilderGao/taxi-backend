package com.jeychan.taxibackend.common.enums;

/**
 * @author 杨世谨
 */
public enum BizErrorCode {

    /**
     * 错误代码
     */
    NOT_FOUND_ERROR(999, "未知错误"),

    USER_ALREADY_EXISTED(1001, "用戶已存在"),
    USER_NOT_EXISTED(1002, "用戶不存在"),

    PARAMETER_ERROR(2000, "参数有误"),

    DAYS_NOT_FOUND(2001, "元表不存在"),


    PARAM_INVALID(3001, "参数不合法"),
    PARAM_LACK(3002, "参数不全"),
    ;

    BizErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private int code;
    private String desc;

    public BizErrorCode findByCode(int code) {
        for (BizErrorCode bizErrorCode : BizErrorCode.values()) {
            if (bizErrorCode.getCode() == code) {
                return bizErrorCode;
            }
        }
        return NOT_FOUND_ERROR;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}