package com.k.community.util;

/**
 * @ClassName: CommunityConstant
 * @Description: CommunityConstant
 * @Author 77166
 * @Date 2021/3/30
 */
public interface CommunityConstant {
    /**
     * 激活成功
     */
    int ACTIVATION_SUCCESS=0;
    /**
     * 重复激活
     */
    int ACTIVATION_REPEAT=1;

    /**
     * 校验失败
     */
    int ACTIVATION_ERROR=2;

    /**
     * 默认登陆凭证保存时间:12hours
     */
    int DEFAULT_EXPIRED_SECONDS=3600*12;

    /**
     * 勾选记住我登陆凭证保存时间:30days
     */
    int REMEMBER_EXPIRED_SECONDS=3600*24*30;

}

