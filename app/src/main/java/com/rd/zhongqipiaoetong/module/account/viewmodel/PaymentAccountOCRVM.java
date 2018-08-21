package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.intsig.idcardscan.sdk.IDCardScanSDK;
import com.intsig.idcardscan.sdk.ISCardScanActivity;
import com.intsig.idcardscan.sdk.ResultData;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BaseParams;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.databinding.AccountPaymentAccountOcrBinding;
import com.rd.zhongqipiaoetong.module.account.activity.PaymentAccountOCRAct;
import com.rd.zhongqipiaoetong.module.account.model.AppPaymentMo;
import com.rd.zhongqipiaoetong.module.account.model.LoginingMo;
import com.rd.zhongqipiaoetong.module.account.model.TokenMo;
import com.rd.zhongqipiaoetong.module.more.activity.RDWebViewAct;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.network.api.AccountService;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/25 18:09
 * <p/>
 * Description: 支付账户VM({@link PaymentAccountOCRAct})
 */
public class PaymentAccountOCRVM {
    private AccountPaymentAccountOcrBinding binding;
    private LoginingMo                      loginingmo;
    public         ObservableField<Boolean> enable           = new ObservableField<>(false);
    private static int                      REQ_CODE_CAPTURE = 111;
    public         ObservableField<String>  name             = new ObservableField<>("");
    public         ObservableField<String>  idCard           = new ObservableField<>("");
    private        boolean                  fontEnable       = false;
    private        boolean                  backEnable       = false;
    private String frontPicQN;
    private String backPicQN;

    public PaymentAccountOCRVM(AccountPaymentAccountOcrBinding binding) {
        this.binding = binding;
    }

    /**
     * 开通支付账户
     */
    public void onRegisterClick(View view) {

        //        String imei = SPUtil.imeiSave(view.getContext());
        uploadIdcard();
        //        res_data(realname, cardId, imei);
    }

    /**
     * 开户（实名认证）
     */
    private void res_data() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.REALNAME, name.get());
        map.put(RequestParams.IDNO, idCard.get());
        //        map.put(RequestParams.ACKTOKEN, imei);
        //        map.put(RequestParams.ACKAPPKEY, RequestParams.ACKAPPKEY_NUM);
        RDPayment.getInstance().getPayController().doPayment(null, PayController.TPYE_REGISTER, map, null);
    }

    /**
     * OCR拍摄
     */
    public void ocrScan(View view) {
        Intent intent = new Intent();
        intent.putExtra(ISCardScanActivity.EXTRA_KEY_IMAGE_FOLDER,
                "/sdcard/idcardscan/");
        intent.putExtra(ISCardScanActivity.EXTRA_KEY_COLOR_MATCH,
                0xffff0000);
        intent.putExtra(ISCardScanActivity.EXTRA_KEY_COLOR_NORMAL,
                0xff00ff00);
        intent.putExtra(ISCardScanActivity.EXTRA_KEY_APP_KEY, BaseParams.OCRAPPKEY);
        intent.putExtra(ISCardScanActivity.EXTRA_KEY_TIPS, "请将身份证放在框内识别");
        //IDCardScanSDK.OPEN_COMOLETE_CHECK 表示完整性判断，
        //IDCardScanSDK.CLOSE_COMOLETE_CHECK或其它值表示关闭完整判断
        intent.putExtra(ISCardScanActivity.EXTRA_KEY_COMPLETECARD_IMAGE,
                IDCardScanSDK.OPEN_COMOLETE_CHECK);
        ActivityUtils.push(ISCardScanActivity.class, intent, REQ_CODE_CAPTURE);
//        ((Activity) view.getContext()).startActivityForResult(intent, REQ_CODE_CAPTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_CAPTURE && data != null) {
            ResultData result = (ResultData)
                    data.getSerializableExtra(ISCardScanActivity.EXTRA_KEY_RESULT_DATA);
            if (result != null) {
                if (result.isFront()) {
                    name.set(result.getName());
                    idCard.set(result.getId());
                }
                setImage(result.isFront(), result.getOriImagePath());
            }
        }
    }

    /** 上传身份证 */
    private void uploadIdcard() {
        Call<AppPaymentMo> call = RDClient.getService(AccountService.class).OCRImage(name.get(), idCard.get(), frontPicQN, backPicQN);
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<AppPaymentMo>() {
            @Override
            public void onSuccess(Call<AppPaymentMo> call, Response<AppPaymentMo> response) {
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.TITLE, ActivityUtils.peek().getString(R.string.payment_account_realname));
                intent.putExtra(BundleKeys.URL, response.body().getUrl());
                ActivityUtils.push(RDWebViewAct.class, intent, PayController.REQUEST_CODE_REGISTER);
            }
        });
    }

    /** 设置图片 */
    private void setImage(boolean isFont, String fileUrl) {
        Bitmap cropBitmap = BitmapFactory.decodeFile(fileUrl);
        if (isFont) {
            binding.realnameIdcardFan.setImageBitmap(cropBitmap);
            req_uploadImg(isFont, fileUrl);
        } else {
            binding.realnameIdcardZheng.setImageBitmap(cropBitmap);
            req_uploadImg(isFont, fileUrl);
        }
    }

    private void checkEnable() {
        if (fontEnable && backEnable) {
            enable.set(true);
        } else {
            enable.set(false);
        }
    }

    /**
     * 上传头像
     */
    private void req_uploadImg(final boolean isFont, final String url) {

        Call<TokenMo> call = RDClient.getInstance().getService(AccountService.class).qiuniuToken();
        call.enqueue(new RequestCallBack<TokenMo>() {
            @Override
            public void onSuccess(Call<TokenMo> call, Response<TokenMo> response) {
                UploadManager uploadManager = new UploadManager();
                File          data          = new File(url);
                uploadManager.put(data, null, response.body().getToken(), new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if (isFont) {
                            fontEnable = true;
                            frontPicQN = response.optString("key");
                        } else {
                            backEnable = true;
                            backPicQN = response.optString("key");
                        }
                        checkEnable();
                    }
                }, null);
            }
        });
    }
}