package me.lam.maidong.weixin;

/**
 * Created by Administrator on 2016/1/20.
 */
public class weixincodecallback {


    /**
     * access_token : OezXcEiiBSKSxW0eoylIeFAYSepBDVc4MqFtyvD-uFUD17qvZy950FWpnmmyji4BJMYguEGKFwlPeO99YU5aSOL-otEETQ1CADPCk2k4l2APqe4bH_QQag2TRZMbvz5B5ndGN3Zfye59eMWQ_hQh3g
     * expires_in : 7200
     * refresh_token : OezXcEiiBSKSxW0eoylIeFAYSepBDVc4MqFtyvD-uFUD17qvZy950FWpnmmyji4BxXjcOGE90Cs4ahWA6WI9rYtXz775Ow4G0m8crfafB8GGeL2ycw1ePz_3OT3GqZ-VPdnEtCCDzq99TKr5RCWE6A
     * openid : ofoxWsyfubSF033W8mSbt6XMcAEU
     * scope : snsapi_userinfo
     * unionid : oqbnUwAjea5iU6TT21t6-f1LOk44
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public String getScope() {
        return scope;
    }

    public String getUnionid() {
        return unionid;
    }
}
