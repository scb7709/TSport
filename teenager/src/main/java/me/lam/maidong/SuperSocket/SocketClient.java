package me.lam.maidong.SuperSocket;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketTimeoutException;

import anet.channel.util.StringUtils;
import me.lam.maidong.myview.MyToash;
import me.lam.maidong.utils.DataTransferUtils;

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


    /**
     * @param host    ip地址
     * @param timeOut 链接超市时间 秒
     * @param// port端口号
     */
    public SocketClient(String host, int port, int timeOut, ICoallBack CoallBack) {
        this.host = host;
        this.port = port;
        this.timeout = timeOut;
        this.CoallBack = CoallBack;
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

    MsgReceiveThread msgReceiveThread;
    //  OutputStream serverOutput;
    public Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == CONNECTSUCCESS) {
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

        MyToash.Log("2");
        Thread thread = new Thread(new Runnable() {


            @Override
            public void run() {
                MyToash.Log("3");
                Message message = new Message();
                try {
                    socket = new Socket(host, port);
                    socket.setSoTimeout(timeout * 3000);
                    MyToash.Log("SocketClient  Socket链接成功");
                    message.what = CONNECTSUCCESS;
                    handler.sendMessage(message);
                    MyToash.Log("4");
                } catch (Exception e1) {
                    MyToash.Log("5");
                    message.what = CONNECTFAILURE;
                    message.obj = e1;
                    e1.printStackTrace();
                    MyToash.Log("SocketClient  连接失败");

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
            msgReceiveThread.STOP_RECEIVE = true;

        } catch (Exception e) {
        }

        try	{
//关闭输出
            if(serverOutput!=null) {
                serverOutput.close();
            }
        }catch(Exception e) {
            //log.error("SocketClient:Exception when closeConnection()-" + e);
        }
        try{
//关闭输入
            if(msgReceiveThread.dataInputStream!=null) {
                msgReceiveThread.dataInputStream.close();
            }
        }catch(Exception e) {
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
     *
     *      public int VERSION { get; set; }
     public int ENCRYPT { get; set; }
     public int STATUS { get; set; }
     public string TOKEN { get; set; }
     public int COMMAND { get; set; }
     public int MODE { get; set; }
     public int LENGTH { get; set; }
     public byte[] DATA { get; set; }


     *
     * @param sndStr
     * @return boolean
     */
    public boolean SenddData(String sndStr) {
        MyToash.Log("JIANYUid"+sndStr);
        JSONObject jsonObject= new JSONObject();
        try {
            if (serverOutput != null) {
                String str="";
                byte [] data=new byte[52];
                byte [] bytes1=hexStr2ByteArray("010000000000");
                String token="8144771b856c46c0adbb5d14a09311c6";
                byte [] bytes2= DataTransferUtils.get4Bytes(00001, 4);
                byte [] bytes3=hexStr2ByteArray("010000000023235F2A2A");
                for(int  i=0;i<6;i++) {
                    data[i]=bytes1[i];
                    MyToash.Log("data["+i+"]="+data[i]+"");
                    str+=data[i];
                }
                for(int  i=0;i<32;i++) {
                    data[i+6]= (byte)(int)Integer.valueOf(token.charAt(i));
                    MyToash.Log("data["+(i+6)+"]="+data[i+6]+"");
                    str+=data[i+6];
                }
                for(int  i=0;i<4;i++) {
                    data[i+38]= bytes2[i];
                    MyToash.Log("data["+(i+38)+"]="+data[i+38]+"");
                    str+=data[i+38];
                }
                for(int  i=0;i<10;i++) {
                    data[i+42]= bytes3[i];
                    MyToash.Log("data["+(i+42)+"]="+data[i+42]+"");
                    str+=data[i+42];
                }
                MyToash.Log(str);
                serverOutput.write(data);
                serverOutput.flush();
               // serverOutput.close();
                MyToash.Log("已发送");
                return true;
            } else {
                MyToash.Log("serverOutput==null");
                return false;
            }


        } catch (SocketTimeoutException ste) {
            MyToash.Log("发送超时");
            //closeConnection();
            return false;


        } catch (Exception e) {
            MyToash.Log("发送异常");
            return false;


        }


    }



    /**
     * @param
     * @return
     */
    public byte[] toByteArray(Object obj)
    {
        try
        {
            return obj.toString().getBytes("utf-8");
        }
        catch (UnsupportedEncodingException e)
        {
            MyToash.Log("JIANYUidBUDENGYUKON");
            return null;
        }

    }

    //16进制字符串转化字节数组
    public static byte[] hexStr2ByteArray(String hexString) {
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {
            //因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            //将hex 转换成byte   "&" 操作为了防止负数的自动扩展
            // hex转换成byte 其实只占用了4位，然后把高位进行右移四位
            // 然后“|”操作  低四位 就能得到 两个 16进制数转换成一个byte.
            //
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    public static String string2Unicode(String string) {
        if (StringUtils.isBlank(string)) return null;
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            String hex = Integer.toHexString(c);
            MyToash.Log(hex);
            if (hex.length() == 2) {//非中文补全两个00
                unicode.append(hex+"00");
            } else {//中文颠倒顺序
                unicode.append(hex.substring(2,4)+hex.substring(0,2));
            }

        }
        return unicode.toString();
    }

}
