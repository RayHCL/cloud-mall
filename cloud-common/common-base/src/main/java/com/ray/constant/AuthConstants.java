package com.ray.constant;

/**
 * @author Ray
 * @description 认证授权常量
 * @date 2024/08/04
 */
public class AuthConstants {

    /**
     * 携带token请求头中的key
     */
    public final static String AUTHORIZATION = "Authorization";

    /**
     * token值的前缀
     */
    public final static String BEARER = "Bearer ";

    /**
     * redis中存放token值的前缀
     */
    public final static String LOGIN_TOKEN_PREFIX = "login_token:";

    /**
     * 登录的类型
     */
    public final static String LOGIN_TYPE = "loginType";

    /**
     * 管理员登录标记
     */
    public final static String SYS_USER_LOGIN = "sysUserLogin";

    /**
     * 会员登录标记
     */
    public final static String MEMBER_LOGIN = "memberLogin";

    /**
     * 登录的路径
     */
    public final static String LOGIN_URL = "/doLogin";

    /**
     * 登出的路径
     */
    public final static String LOGIN_OUT = "/doLogout";


    /**
     * TOKEN有效时长（单位：秒，4个小时）
     */
    public final static Long TOKEN_TIME = 14400L;

    /**
     * TOKEN的阈值：3600秒（1个小时）
     */
    public final static Long TOKEN_EXPIRE_THRESHOLD_TIME = 60*60L;

}
