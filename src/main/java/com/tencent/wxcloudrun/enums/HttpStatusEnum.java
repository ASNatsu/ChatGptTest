package com.tencent.wxcloudrun.enums;

public enum HttpStatusEnum {
    SUCCESS(200, "success"),
    REJECT(400, "reject"),
    NO_LOGIN(401, "当前用户账号已在其它地址登录或已超时退出，请重新登录！"),
    NO_AUTH(403, "no auth"),
    FAIL(500, "fail"),
    NOT_FROM_GATEWAY(400, "illegal request, not from gateway!"),
    IDEMPOTENT(88403, "请求重复, 幂等性校验失败");

    private final int code;
    private final String desc;

    HttpStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
