package me.lam.maidong.SuperSocket;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * Created by abc on 2018/1/3.
 */
/**
 * @author 发送消息线程
 */
public class SendMsgThread extends Thread implements Runnable {
    Handler handler;
    OutputStream serverOutput;
    Object msg = null;


    public SendMsgThread(Socket Client, Handler handler, Object msg) {
        try {
            serverOutput = Client.getOutputStream();
            this.msg = msg;
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.handler = handler;
    }


    @Override
    public void run() {
        super.run();
        Message message = new Message();
        try {
            byte[] data = toByteArray(msg);
            serverOutput.write(data);
            serverOutput.flush();
            message.what = SocketClient.SENDSUCCESS;
        } catch (Exception ste) {
            message.what = SocketClient.SENDFAILURE;
            message.obj = ste;
        }
        handler.sendMessage(message);
    }


    /**
     * //@param obj转换byte
     * @return
     */
    public byte[] toByteArray(Object obj) {
        try {
            return obj.toString().getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }
}
