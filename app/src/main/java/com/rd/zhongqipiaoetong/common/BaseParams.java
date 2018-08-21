package com.rd.zhongqipiaoetong.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.rd.zhongqipiaoetong.utils.ActivityUtils;

import java.io.File;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/23 15:22
 * <p/>
 * Description:
 */
public class BaseParams {
    /**
     * 是否是debug模式
     */
    public static final  boolean IS_DEBUG            = true;
    /**
     * 支付类型 1双乾 2汇付 4UFX 3联动优势
     */
    public static final  int     paymentType         = 4;
    /**
     * 分享布局类型
     * 1 云默认  2杭文投
     */
    public static final  int     inviteType          = 2;
    /**
     * OCRkey
     */
    public static final  String  OCRAPPKEY           = "VdB1gDXNPJh6VHbCdU5hbfT1";
    /**
     * 测试服务器地址
     */
    private static final String  URL_BETA            = "http://101.254.136.221:6002";
    public static final  String  FRIDAY_ADDRESS      = "https://friday.erongdu.com/mobile/";
    /**
     * 正式服务器地址
     */
    private static final String  URL_RELEASE         = "https://api.p2p.erongyun.com";
    /**
     * 服务器地址
     */
    public static final  String  URL_ADDRESS         = IS_DEBUG ? URL_BETA : URL_RELEASE;
    /**
     * 模块名称("接口地址"会拼接在 URL 中最后的"/"之后，故URL最好以"/"结尾)
     */
    public static final  String  URL_SCHEME          = "/app/";
    /**
     * appkey
     */
    public static final  String  APP_KEY             = "cccc8bc3d6630bf84a08f9ba361baea0";
    /**
     * app_secret
     */
    public static final  String  APP_SECRET          = "db71ba971de545590a39e7dfa51ded2f";
    /**
     * friday appkey
     */
    public static final  String  FRIDAY_APPKEY       = "cccc8bc3d6630bf84a08f9ba361baea0";
    /**
     * friday app secret
     */
    public static final  String  FRIDAY_APPSECRET    = "db71ba971de545590a39e7dfa51ded2f";
    /**
     * ios传“IOS”，安卓传“ANDROID”
     */
    public static final  String  MOBILE_TYPE         = "ANDROID";
    /**
     * 发送验证码的短信平台号
     */
    public static final  String  SMS_SENDER          = "";
    /**
     * 加密是需要使用的密钥
     * DES加解密时KEY必须是16进制字符串,不可小于8位
     * E.G.    6C4E60E55552386C-
     * <p/>
     * 3DES加解密时KEY必须是6进制字符串,不可小于24位
     * E.G.    6C4E60E55552386C759569836DC0F83869836DC0F838C0F7
     */
    public static final  String  SECRET_KEY          = "6C4E60E55552386C759569836DC0F83869836DC0F838C0F7";
    /**
     * 根路径
     */
    public static final  String  ROOT_PATH           = getSDPath() + "/Ultron";
    /**
     * crash文件保存路径
     */
    public static final  String  CRASH_PATH          = ROOT_PATH + "/crashLog";
    /**
     * SP文件名
     */
    public static final  String  SP_NAME             = "Ultron_params";
    public static final  String  SP_LOCK             = "lock_";
    public static final  String  SP_SIGN_OUT_TIME    = "sign_out_time";
    public static final  String  SP_IS_FIRST_INE     = "is_first_in";
    /**
     * 微信APPKey APPSecret
     */
    public static final  String  APPKeyWX            = "wx75096ef724742d5f";//其他项目的key 需要替换
    public static final  String  APPSecretWx         = "775207436cc9287b2e76a11803d3a9f9";//其他项目的key 需要替换
    /**
     * 微博APPKey APPSecret
     */
    public static final  String  APPKeySina          = "3645032709";//其他项目的key 需要替换
    public static final  String  APPSecretSina       = "a4d989061815b4b80034b452feb829bb";//其他项目的key 需要替换
    /**
     * 微信借款描述
     */
    public static final  String  WX_DESCRIPTION      = "/app/h5/borrow_info.html";
    /**
     * 微信借款资料
     */
    public static final  String  WX_INFO             = "/app/h5/borrow_uploads.html";
    /**
     * 流转标借款描述
     */
    public static final  String  WX_FLOW_DESCRIPTION = "/app/tender/tenderInfomation";
    /**
     * 流转标借款资料
     */
    public static final  String  WX_FLOW_INFO        = "/app/tender/tenderInfomation";
    /**
     * 上传头像地址
     */
    public static final  String  TOIMAGE             = "/app/account/headUploading";
    /**
     * 获取tokenKey
     */
    public static final  String  TOKENKEY            = "/qiniu/uptoken";
    /**
     * 上传身份证地址
     */
    public static final  String  UPLOADIDCARD        = "/app/user/certFileUpload";
    /**
     * 路桥民融标识符
     */
    public static final  int     SPECIALLAYOUT       = 1;

    /**
     * 获取SD卡的根目录
     */
    public static String getSDPath() {
        File sdDir = null;
        // 判断sd卡是否存在
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            // 获取跟目录
            sdDir = Environment.getExternalStorageDirectory();
        }
        if (sdDir == null) {
            return "";
        } else {
            return sdDir.toString();
        }
    }

    /**
     * 获取VersionCode
     */
    public static int getVersion() {
        try {
            Context        context = ActivityUtils.peek();
            PackageManager pm      = context.getPackageManager();//context为当前Activity上下文
            PackageInfo    pi      = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 1;
        }
    }

    /**
     * 获取VersionCode
     */
    public static String getVersionName() {
        try {
            Context        context = ActivityUtils.peek();
            PackageManager pm      = context.getPackageManager();//context为当前Activity上下文
            PackageInfo    pi      = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "--";
        }
    }

    /**
     * 首页extraButton点击链接
     */
    public static final String EXTRA_BUTTON_URL = "http://www.baidu.com";
}
