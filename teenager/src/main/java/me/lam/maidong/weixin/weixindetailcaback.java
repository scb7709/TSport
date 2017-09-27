package me.lam.maidong.weixin;

import java.util.List;

/**
 * Created by Administrator on 2016/1/20.
 */
public class weixindetailcaback {
    /**
     * openid : ofoxWsyfubSF033W8mSbt6XMcAEU
     * nickname : 黄国榕
     * sex : 1
     * language : zh_CN
     * city : 廊坊
     * province : 河北
     * country : 中国
     * headimgurl : http://wx.qlogo.cn/mmopen/1iaB7n40KRPeD6sMgOO8Bufh6rKThHyIOOVqGHibfOzyMNoA2MZHoxtQicxx39yaKeJOuCJBA2T8DeRP2ias4aICTJYUy6ynwymq/0
     * privilege : []
     * unionid : oqbnUwAjea5iU6TT21t6-f1LOk44
     */

    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private String unionid;
    private List<?> privilege;

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public void setPrivilege(List<?> privilege) {
        this.privilege = privilege;
    }

    public String getOpenid() {
        return openid;
    }

    public String getNickname() {
        return nickname;
    }

    public int getSex() {
        return sex;
    }

    public String getLanguage() {
        return language;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getCountry() {
        return country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public List<?> getPrivilege() {
        return privilege;
    }


 /*   https://api.weixin.qq.com/sns/userinfo?access_token=OezXcEiiBSKSxW0eoylIeFAYSepBDVc4MqFtyvD-uFUD17qvZy950FWpnmmyji4BJMYguEGKFwlPeO99YU5aSOL-otEETQ1CADPCk2k4l2APqe4bH_QQag2TRZMbvz5B5ndGN3Zfye59eMWQ_hQh3g&openid=ofoxWsyfubSF033W8mSbt6XMcAEU&lang=zh_CN
*/







}
