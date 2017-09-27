package me.lam.maidong.entity;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by abc on 2017/8/4.
 * 所有网络请求返回的公共数据解析类
 */
public class PublicDataClass<T> implements Serializable {
    public  StatusModel StatusModel;
    public  T  Data;
    public  class StatusModel {
        public int StatusCode;
        public String StatusMessage;

        @Override
        public String toString() {
            return "MdResponse{" +
                    "StatusCode=" + StatusCode +
                    ", StatusMessage='" + StatusMessage + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PublicDataClass{" +
                "MdResponse=" + StatusModel +
                ", Data=" + Data +
                '}';
    }
}
