package me.lam.maidong.SuperSocket;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

import me.lam.maidong.myview.MyToash;

/**
 * Created by abc on 2018/1/3.
 */

/**
 * android Socket客户端封装
 */

@SuppressLint("HandlerLeak")
public class SocketClient {
    private Socket socket;
    private OutputStream serverOutput = null;
    private String host;
    private int port = -1;
    private int timeout = 3;
    private ICoallBack CoallBack = null;
    private ISocketPacket _packet = null;
    private ISendResult _Result = null;
    public static boolean SERVER_IS_DISCONNECT;


    /**
     * @param host    ip地址
     * @param timeOut 链接超市时间 秒
     * @param// port端口号
     */
    public SocketClient(String host, int port, int timeOut, ICoallBack CoallBack, ISocketPacket iSocketPacket) {
        this.host = host;
        this.port = port;
        this.timeout = timeOut;
        this.CoallBack = CoallBack;
        this._packet = iSocketPacket;
    }

    public Socket getSocket() {
        return socket;
    }

    /**
     * @param //设置连接服务器监听
     */
    public void setOnConnectListener(ICoallBack CoallBack) {
        this.CoallBack = CoallBack;
    }


    /**
     * 连接成功
     **/
    public static final int CONNECTSUCCESS = 1;
    /**
     * 连接失败
     **/
    public static final int CONNECTFAILURE = -1;
    /**
     * 接收消息
     **/
    public static final int RECEIVEMESSAGE = 2;
    /**
     * 发送消息
     **/
    public static final int SENDMESSAGE = 3;
    /**
     * 发送成功
     **/
    public static final int SENDSUCCESS = 4;
    /**
     * 发送失败
     **/
    public static final int SENDFAILURE = -2;
    public int Again_CONNECT_COUNT = 5;//重连次数
    public static final int Again_CONNECT = 5;//重连

    MsgReceiveThread msgReceiveThread;
    //  OutputStream serverOutput;
    public Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == CONNECTSUCCESS) {
                Again_CONNECT_COUNT=5;
                msgReceiveThread = new MsgReceiveThread(socket, handler);
                msgReceiveThread.STOP_RECEIVE = false;
                msgReceiveThread.start();
                if (CoallBack != null) {
                    try {
                        MyToash.Log("SocketClient  CoallBack监听");
                        serverOutput = socket.getOutputStream();
                        CoallBack.OnSuccess(socket);
                    } catch (IOException e) {
                        MyToash.Log("SocketClient  获取getOutputStream异常");
                        e.printStackTrace();
                    }

                }

            } else if (msg.what == CONNECTFAILURE) {
                Exception e1 = (Exception) msg.obj;
                if (CoallBack != null) {
                    CoallBack.OnFailure(e1);
                }
                if (msgReceiveThread != null) {
                    msgReceiveThread.STOP_RECEIVE = true;
                }
                closeConnection();

            } else if (msg.what == RECEIVEMESSAGE) {
                if (_packet != null) {
                    _packet.SocketPacket(msg.obj.toString());
                }
            } else if (msg.what == SENDMESSAGE) {
                new SendMsgThread(socket, handler, msg.obj).start();


            } else if (msg.what == SENDSUCCESS) {
                if (_Result != null) {
                    _Result.OnSendSuccess();
                }
            } else if (msg.what == SENDFAILURE) {
                if (_Result != null) {
                    Exception e = (Exception) msg.obj;
                    _Result.OnSendFailure(e);
                }
            } else if (msg.what == Again_CONNECT) {
                Connection();
            }


        }
    };


    /**
     * //@param _packet设置接受消息监听
     */
    public void setOnReceiveListener(ISocketPacket _packet) {
        this._packet = _packet;
    }


    /**
     * 打开连接
     *
     * @return boolean
     */
    public void Connection() {

        //MyToash.Log("2");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // MyToash.Log("3");
                Message message = new Message();
                try {
                    socket = new Socket(host, port);
                    socket.setSoTimeout(timeout * 3000);
                    MyToash.Log("SocketClient  Socket链接成功");
                    message.what = CONNECTSUCCESS;
                    handler.sendMessage(message);
                    //   MyToash.Log("4");
                } catch (Exception e1) {
                    if (Again_CONNECT_COUNT > 0) {
                        handler.sendEmptyMessageDelayed(Again_CONNECT, Again_CONNECT / 2);//2.5秒重连一次
                        MyToash.Log("SocketClient  重新连接" + Again_CONNECT_COUNT);
                    } else {
                        message.what = CONNECTFAILURE;
                        message.obj = e1;
                        handler.sendMessage(message);
                        e1.printStackTrace();
                        MyToash.Log("SocketClient  连接失败");
                    }
                    --Again_CONNECT_COUNT;
                }


            }
        });
        thread.start();
    }


    /**
     * 关闭连接的输入输出流
     *
     * @author mick.ge
     */
    public void closeConnection() {
        CoallBack.OnFailure(null);
        try {
            if (socket != null) {
                socket.close();// 关闭socket
            }

        } catch (Exception e) {
        }
        try {
            msgReceiveThread.STOP_RECEIVE = true;
        } catch (Exception e) {
        }
        try {
//关闭输出
            if (serverOutput != null) {
                serverOutput.close();
            }
        } catch (Exception e) {
            //log.error("SocketClient:Exception when closeConnection()-" + e);
        }
        try {
//关闭输入
            if (msgReceiveThread.dataInputStream != null) {
                msgReceiveThread.dataInputStream.close();
            }
        } catch (Exception e) {
            //log.error("SocketClient:Exception when closeConnection()-" + e);
        }
    }


    public void SenddData(Object msg, ISendResult _Result) {
        this._Result = _Result;
        Message message = new Message();
        message.obj = msg;
        message.what = SENDMESSAGE;
        handler.sendMessage(message);
    }


    /**
     * 发送数据
     * <p/>
     * public int VERSION { get; set; }
     * public int ENCRYPT { get; set; }
     * public int STATUS { get; set; }
     * public string TOKEN { get; set; }
     * public int COMMAND { get; set; }
     * public int MODE { get; set; }
     * public int LENGTH { get; set; }
     * public byte[] DATA { get; set; }
     *
     * @param sndStr
     * @return boolean
     */
    //  String string = "1 0 0 0 0 0 56 49 52 52 55 55 49 98 56 53 54 99 52 54 99 48 97 100 98 98 53 100 49 52 97 48 57 51 49 49 99 54 0 1 59 -87 8 0 0 0 32 0 0 0 4 100 97 116 97 35 35 95 42 42";
    public boolean SenddData(String sndStr, boolean is_connect) {

        if (sndStr == null || sndStr.length() != 32) {
            return false;
        }
        MyToash.Log("JIANYUid" + sndStr + "  " + is_connect);
        try {
            serverOutput = socket.getOutputStream();
            if (serverOutput != null) {
                String str = "";
                byte[] data = new byte[60];
                for (int i = 0; i < 6; i++) {
                    if (i == 0) {
                        data[i] = 1;
                    } else {
                        data[i] = 0;
                    }
                    MyToash.Log("data[" + (i) + "]=" + data[i] + "");
                    str += data[i] + " ";
                }

                byte bytes1[] = sndStr.getBytes("utf-8");
                for (int i = 0; i < 32; i++) {
                    data[i + 6] = bytes1[i];
                    MyToash.Log("data[" + (i + 6) + "]=" + data[i + 6] + "");
                    str += data[i + 6] + " ";
                }
                byte[] bytes2;
                if (!is_connect) {
                    byte[] tempbytes2 = {0, 0, (byte) 237, (byte) 237, 8, 0, 0, 0, 32, 0, 0, 0, 4, 100, 97, 116, 97, 35, 35, 95, 42, 42};
                    bytes2 = tempbytes2;
                } else {
                    byte[] tempbytes2 = {0, 0, (byte) 237, (byte) 137, 8, 0, 0, 0, 32, 0, 0, 0, 4, 100, 97, 116, 97, 35, 35, 95, 42, 42};
                    bytes2 = tempbytes2;
                }
                for (int i = 0; i < 22; i++) {
                    data[i + 38] = bytes2[i];
                    MyToash.Log("data[" + (i + 38) + "]=" + data[i + 38] + "");
                    str += data[i + 38] + " ";

                }
                MyToash.Log(str);
                serverOutput.write(data);
                serverOutput.flush();
                SERVER_IS_DISCONNECT = false;
                return true;
            } else {
                MyToash.Log("serverOutput==null");
                SERVER_IS_DISCONNECT = true;
                return false;
            }


        } catch (SocketTimeoutException ste) {
            MyToash.Log("发送超时");
            //closeConnection();
            SERVER_IS_DISCONNECT = true;
            return false;


        } catch (Exception e) {
            MyToash.Log("发送异常" + e.getMessage());
            MyToash.Log("发送异常");
            SERVER_IS_DISCONNECT = true;
            return false;


        }


    }


    public boolean SenddDataCheck(byte[] CHECK_CONNECT) {
        try {
            serverOutput = socket.getOutputStream();
            if (serverOutput != null) {
                serverOutput.write(CHECK_CONNECT);
                serverOutput.flush();
                SERVER_IS_DISCONNECT = false;
                return true;
            } else {
                MyToash.Log("serverOutput==null");
                SERVER_IS_DISCONNECT = true;
                return false;
            }
        } catch (SocketTimeoutException ste) {
            MyToash.Log("发送超时");
            //closeConnection();
            SERVER_IS_DISCONNECT = true;
            return false;


        } catch (Exception e) {
            MyToash.Log("发送异常" + e.getMessage());
            MyToash.Log("发送异常");
            SERVER_IS_DISCONNECT = true;
            return false;


        }


    }
}
