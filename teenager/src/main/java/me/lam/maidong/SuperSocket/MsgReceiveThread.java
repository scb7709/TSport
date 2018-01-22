package me.lam.maidong.SuperSocket;

/**
 * Created by abc on 2018/1/3.
 */


import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import me.lam.maidong.myview.MyToash;

/**
 * @author 接收消息线程
 */
public class MsgReceiveThread extends Thread implements Runnable {
    Socket _Client;
    Handler handler;
    BufferedReader in;
    boolean STOP_RECEIVE;
    public    DataInputStream dataInputStream;
    int count=0;
    public MsgReceiveThread(Socket _Client, Handler handler) {
        this._Client = _Client;
        this.handler = handler;
    }


    @Override
    public void run() {
        super.run();
        while (!STOP_RECEIVE) {
            try {
                if (_Client != null&&!_Client.isClosed()) {
                   // _Client.setSoTimeout(6000 * 5);//设定超时时间
                // MyToash.Log("服务器返回msg 一直在接收"+(_Client==null));
                    dataInputStream = new DataInputStream(_Client.getInputStream());
                    byte[] buffer;
                    buffer = new byte[dataInputStream.available()];
                    if(buffer.length != 0){
                        dataInputStream.read(buffer);
                        String three = new String(buffer);
                        MyToash.Log("收到服务端消息11         " + three);
                        Message message = new Message();
                        message.what = SocketClient.RECEIVEMESSAGE;
                        message.obj = three;
                        handler.sendMessage(message);
                    }
                }
            } catch (IOException e) {
                MyToash.Log("服务器返回yichang1"+e.getMessage());
                e.printStackTrace();
            }catch (Exception e) {
                MyToash.Log("服务器返回yichang2"+e.getMessage());
                e.printStackTrace();
            }

        }
    }


}






