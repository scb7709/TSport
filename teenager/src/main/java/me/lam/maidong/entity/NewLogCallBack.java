package me.lam.maidong.entity;

/**
 * Created by 1 on 2015/12/8.
 */
public class NewLogCallBack {
    public  String MyStatus;
    public  String StatusMessage;
    public  String EducationCode;
    public  String LastSportDay;

    @Override
    public String toString() {
        return "NewLogCallBack{" +
                "MyStatus='" + MyStatus + '\'' +
                ", StatusMessage='" + StatusMessage + '\'' +
                ", EducationCode='" + EducationCode + '\'' +
                ", LastSportDay='" + LastSportDay + '\'' +
                '}';
    }
    /*

    *//**
     * MyStatus : 1
     * LastSportDay : 无
     * EducationCode : 080
     * StatusMessage : 登录成功！
     *//*
    private int Status;
    private String Message;
    public  Data Data;

    @Override
    public String toString() {
        return "NewLogCallBack{" +
                "Status=" + Status +
                ", Message='" + Message + '\'' +
                ", data=" + Data +
                '}';
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Data getData() {
        return Data;
    }

    public void setData(Data data) {
        this.Data = data;
    }

    public class Data {
        @Override
        public String toString() {
            return "Data{" +
                    "EducationalCode='" + EducationalCode + '\'' +
                    ", LastSportDay='" + LastSportDay + '\'' +
                    '}';
        }

        private String EducationalCode;
        private String LastSportDay;

        public String getEducationalCode() {
            return EducationalCode;
        }

        public void setEducationalCode(String educationalCode) {
            EducationalCode = educationalCode;
        }

        public String getLastSportDay() {
            return LastSportDay;
        }

        public void setLastSportDay(String lastSportDay) {
            LastSportDay = lastSportDay;
        }
    }*/
}
