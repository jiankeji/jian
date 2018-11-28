package com.jian.core.model;

/**
 * 错误码队列
 */
public final class ResC {

    public static final int API_SUCCESS = 200;//成功状态码

    public static final int API_RESTART = 600000;//请重新请求

    public static final int API_EXCEPTION = 600001;//操作异常

    /** 参数错误*/
    public static final int API_PARAMS_ERROR = 600002;

    /** 手机号错误(位数不够、格式不对)*/
    public static final int API_PHONE_NUM_ERROR = 600004;

    /** 用户输入的短信验证码不正确*/
    public static final int API_MSG_VALIDATE_CODE_ERROR = 600005;

    /** 手机号不一致*/
    public static final int API_PHONE_NUM_DIFFER = 600006;

    /** 调用发送手机验证码失败*/
    public static final int API_SEND_PHONE_VALIDATE_CODE_ERROR = 600007;

    /** 手机号已存在,请换其他手机号*/
    public static final int API_PHONE_EXIT = 600008;
    /**参数格式不正确，参数为空之类的*/
    public static final int API_PARAMS_FORMAT_ERROR=600009;

    public static final int API_ERROR_LOGIN_USER=600101;//用户未登录

    public static final int API_ERROR_USER_NULL=600102;//用户不存在

    public static final int API_ERROR_USER_NOTNULL=600103;//用户不存在

    public static final int API_TOKEN_NOTNULL=600104;//token为空

    public static final int API_TOKEN_ERROR=600105;//token认证失败

    public static final int API_ERROR_FOLLOW_USER=600201;//自己不能关注/取消关注自己

    public static final int API_ERROE_FOLLOWING= 600202;//已经关注

    public static final int API_FOLLOWED_USER = 600203;//用户关注/取消关注操作失败

    public static final int API_USER_NOT_POWER = 600204;//用户没有权限

    public static final int API_SEND_VERITY_ERROR = 600301;//发送验证码失败

}
