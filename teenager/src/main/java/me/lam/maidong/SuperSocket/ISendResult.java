package me.lam.maidong.SuperSocket;

/**
 * Created by abc on 2018/1/3.
 */
/**
 * @author 发送接口回调
 */
public interface ISendResult {
    void OnSendSuccess();


    void OnSendFailure(Exception e);
}
