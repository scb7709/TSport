package me.lam.maidong.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 1 on 2015/12/14.
 */
public class msgCallBack implements Serializable{
    /**
     * Status : 1
     * MessageList : [{"MessageContent":"您孩子的运动心率在128-154次/分钟之间，他/她正在进行中等强度的运动。 \r\n每天进行30 分钟以上的中等强度运动与 30分钟以上的高等强度运动，可以在有效改善孩子的\r\n心肺功能与预防肥胖的同时，增进他/她的学习注意力，提高认知功能。 ","MessageTitle":"您的孩子的心率","MessageDate":"2015/9/1 0:00:00","MessageID":"5"},{"MessageContent":"锻炼心率区域是一种比较科学和安全的方法，有助于提高健身健美效果和训练效果 ","MessageTitle":"锻炼心率区域的好处","MessageDate":"2015/6/1 0:00:00","MessageID":"4"},{"MessageContent":"运动对保持身体体形和体能，缓解精神压力，克服肥胖、失眠等症状效果显著，然而要想达\r\n到上述之功效，科学锻炼显得尤为重要。","MessageTitle":"科学运动的重要性","MessageDate":"2015/1/10 0:00:00","MessageID":"3"},{"MessageContent":"心率是反映一个人心肺功能强弱的生理指标。而且心率是在运动锻炼中最常用（使用频率最\r\n高）、最简单易行、最直接明了的一个有效的指标。在一定的运动负荷下，心率反应（波动）小，\r\n说明对运动适应能力强，心肺功能也更好，通俗讲就是身体状况更好。","MessageTitle":"您知道心率的作用吗？","MessageDate":"2014/10/1 0:00:00","MessageID":"2"},{"MessageContent":"您的孩子做得很棒！经常参与运动锻炼可以促进孩子的大脑功能发育，增加学习注意力，提高自\r\n控力；而且，青少年认知功能的发展与孩子每天的运动强度与运动量直接相关，这也决定了他/\r\n她是否可以在平时保持精力充沛的状态。 ","MessageTitle":"您的孩子非常棒！","MessageDate":"2014/8/1 0:00:00","MessageID":"1"}]
     */

    /**
     * Status : 1
     * MessageList : [{"MessageContent":"您孩子的运动心率在128-154次/分钟之间，他/她正在进行中等强度的运动。 \r\n每天进行30 分钟以上的中等强度运动与 30分钟以上的高等强度运动，可以在有效改善孩子的\r\n心肺功能与预防肥胖的同时，增进他/她的学习注意力，提高认知功能。 ","MessageTitle":"您的孩子的心率","MessageDate":"2015/9/1 0:00:00","MessageID":"5"},{"MessageContent":"锻炼心率区域是一种比较科学和安全的方法，有助于提高健身健美效果和训练效果 ","MessageTitle":"锻炼心率区域的好处","MessageDate":"2015/6/1 0:00:00","MessageID":"4"},{"MessageContent":"运动对保持身体体形和体能，缓解精神压力，克服肥胖、失眠等症状效果显著，然而要想达\r\n到上述之功效，科学锻炼显得尤为重要。","MessageTitle":"科学运动的重要性","MessageDate":"2015/1/10 0:00:00","MessageID":"3"},{"MessageContent":"心率是反映一个人心肺功能强弱的生理指标。而且心率是在运动锻炼中最常用（使用频率最\r\n高）、最简单易行、最直接明了的一个有效的指标。在一定的运动负荷下，心率反应（波动）小，\r\n说明对运动适应能力强，心肺功能也更好，通俗讲就是身体状况更好。","MessageTitle":"您知道心率的作用吗？","MessageDate":"2014/10/1 0:00:00","MessageID":"2"},{"MessageContent":"您的孩子做得很棒！经常参与运动锻炼可以促进孩子的大脑功能发育，增加学习注意力，提高自\r\n控力；而且，青少年认知功能的发展与孩子每天的运动强度与运动量直接相关，这也决定了他/\r\n她是否可以在平时保持精力充沛的状态。 ","MessageTitle":"您的孩子非常棒！","MessageDate":"2014/8/1 0:00:00","MessageID":"1"}]
     */
    private int MyStatus;
    private List<MessageListEntity> MessageList;

    public void setStatus(int MyStatus) {
        this.MyStatus = MyStatus;
    }

    public void setMessageList(List<MessageListEntity> MessageList) {
        this.MessageList = MessageList;
    }

    public int getStatus() {
        return MyStatus;
    }


    public List<MessageListEntity> getMessageList() {
        return MessageList;
    }
    public class MessageListEntity implements Serializable {

        @Override
        public String toString() {
            return "MessageListEntity{" +
                    "MessageID=" + MessageID +
                    ", MessageTitle='" + MessageTitle + '\'' +
                    ", MessageContent='" + MessageContent + '\'' +
                    ", MessageDate='" + MessageDate + '\'' +
                    '}';
        }

        private int MessageID;
        private String MessageTitle;

        private String MessageContent;

        private String MessageDate;


        public int getMessageID() {
            return MessageID;
        }

        public void setMessageID(int messageID) {
            MessageID = messageID;
        }

        public String getMessageTitle() {
            return MessageTitle;
        }

        public void setMessageTitle(String messageTitle) {
            MessageTitle = messageTitle;
        }

        public String getMessageContent() {
            return MessageContent;
        }

        public void setMessageContent(String messageContent) {
            MessageContent = messageContent;
        }

        public String getMessageDate() {
            return MessageDate;
        }

        public void setMessageDate(String messageDate) {
            MessageDate = messageDate;
        }
    }


   public class dt
   {
         public String  a;//get set
   }



}
