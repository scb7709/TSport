package me.lam.maidong.SuperSocket;

/**
 * Created by abc on 2018/1/3.
 */

import java.net.Socket;

/**
 * @author 链接服务器回调
 */
public interface ICoallBack {
    /**
     * 连接成功事件
     **/
    void OnSuccess(Socket client);


    /**
     * 连接失败事件
     **/


    void OnFailure(Exception e);
}