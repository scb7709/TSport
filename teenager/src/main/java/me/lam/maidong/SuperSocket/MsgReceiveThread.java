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

/**
 * @author 接收消息线程
 */
public class MsgReceiveThread extends Thread implements Runnable {
    Socket _Client;
    Handler handler;
    BufferedReader in;
    boolean STOP_RECEIVE;
    public    DataInputStream dataInputStream;
    public MsgReceiveThread(Socket _Client, Handler handler) {
        this._Client = _Client;
        this.handler = handler;
    }


    @Override
    public void run() {
        super.run();
        while (!STOP_RECEIVE) {
           // MyToash.Log("收到服务端消息 一直在接收");
            try {
                String line = "";
                if (_Client != null) {
                   // in = new BufferedReader(new InputStreamReader(_Client.getInputStream(),"GBK"));
                   // BufferedReader d = new BufferedReader(new InputStreamReader(_Client.getInputStream()));

                    dataInputStream = new DataInputStream(_Client.getInputStream());
                    byte[] buffer;
                    buffer = new byte[dataInputStream.available()];
                    if(buffer.length != 0){
                        dataInputStream.read(buffer);
                        String three = new String(buffer);
                       // String b1 = new String(three.getBytes("iso-8859-1","utf-8"));

                       // MyToash.Log("收到服务端消息         " + three);
                        Message message = new Message();
                        message.what = SocketClient.RECEIVEMESSAGE;
                        message.obj = three;
                        handler.sendMessage(message);
                    }

                  /*  while ((line = in.readLine()) != null) {
                        //MyToash.Log("收到服务端消息         " + line);
                        Message message = new Message();
                        message.what = SocketClient.RECEIVEMESSAGE;
                        message.obj = line;
                        handler.sendMessage(message);
                    }*/

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
          /*  try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }


}






