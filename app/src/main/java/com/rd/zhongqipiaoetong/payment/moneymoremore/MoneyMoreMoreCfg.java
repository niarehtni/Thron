package com.rd.zhongqipiaoetong.payment.moneymoremore;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/19/16.
 */
public class MoneyMoreMoreCfg {
    private String  privatekey;
    private String  publickey;
    private boolean production;

    public String getPrivatekey() {
        return privatekey;
    }

    public void setPrivatekey(String privatekey) {
        this.privatekey = privatekey;
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public boolean isProduction() {
        return production;
    }

    public void setProduction(boolean production) {
        this.production = production;
    }
}
