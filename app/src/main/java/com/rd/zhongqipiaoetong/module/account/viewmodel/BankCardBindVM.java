package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bigkoo.pickerview.OptionsPickerView;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.Constant;
import com.rd.zhongqipiaoetong.databinding.AccountBankCardBindActBinding;
import com.rd.zhongqipiaoetong.module.account.activity.BankCardBindAct;
import com.rd.zhongqipiaoetong.module.account.model.BindBnakMo;
import com.rd.zhongqipiaoetong.module.account.model.InitialAddBankMo;
import com.rd.zhongqipiaoetong.network.InitRequestCallBack;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.api.BankService;
import com.rd.zhongqipiaoetong.payment.PayCallBack;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.utils.CheckBankNumber;
import com.rd.zhongqipiaoetong.utils.EditTextFormat;
import com.rd.zhongqipiaoetong.utils.Utils;
import com.rd.zhongqipiaoetong.utils.ref.RefException;
import com.rd.zhongqipiaoetong.utils.ref.RefObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/24 17:08
 * <p/>
 * Description: 银行卡绑定VM({@link BankCardBindAct})
 */
public class BankCardBindVM {
    private AccountBankCardBindActBinding mBinding;
    private Context                       mContext;
    /**
     * 绑卡所需数据Bean
     */
    private BindBnakMo                    mbindBnakMo;
    /**
     * 初始化信息
     */
    public final ObservableField<InitialAddBankMo> initialAddBank = new ObservableField<>();
    /**
     * 开户银行选择 PickerView
     */
    public  OptionsPickerView  opendingPickerView;
    /**
     * 地区选择 PickerView
     */
    public  OptionsPickerView  areaPickerView;
    /**
     * 键盘控制
     */
    private InputMethodManager imm;
    /**
     * "确认添加"按钮是否可用
     */
    public final ObservableField<Boolean> enable = new ObservableField<>(false);

    public BankCardBindVM(AccountBankCardBindActBinding binding) {
        mBinding = binding;
        mContext = binding.getRoot().getContext();

        imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        mbindBnakMo = new BindBnakMo();
        res_toaddBank();
        //        res_bank();
        // 银行卡帐号输入框做“4位空格”格式化
        EditTextFormat.bankCardNumAddSpace(binding.bankCardNum, new EditTextFormat.EditTextFormatWatcher() {
            @Override
            public void OnTextWatcher(String str) {
                bankCardNumCheck(str);
            }
        });
        initItemClick();
    }

    /**
     * 开户银行click事件
     * 关闭键盘
     */
    public void banknameClick(View view) {
        opendingPickerView.show();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 地区选择click事件
     * 关闭键盘
     */
    public void areaClick(View view) {
        if (areaPickerView != null) {
            areaPickerView.show();
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } else {
//            res_version();
        }
    }

    private void initItemClick() {
//        res_version();
        //开户行 OptionsPickerView 初始化
        opendingPickerView = new OptionsPickerView(mContext);
        opendingPickerView.setTitle(mContext.getString(R.string.dialog_bindbank));
        opendingPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mBinding.bankname.setText(initialAddBank.get().getBankList().get(options1).getPickerViewText());
                mbindBnakMo.setBank(initialAddBank.get().getBankList().get(options1).getBankCode());
                validator();
            }
        });

//        //读取本地area信息
//        final ProvinceListMo provinceListMo = SPUtil.getEntity(ProvinceListMo.class, null);
//        setAreaData(provinceListMo);
    }

//    /**
//     * 设置地区areaPickerView
//     *
//     * @param provinceListMo
//     */
//    private void setAreaData(final ProvinceListMo provinceListMo) {
//        if (provinceListMo == null) {
//            return;
//        }
//        //地区选择 OptionsPickerView 初始化
//        if (areaPickerView == null) {
//            areaPickerView = new OptionsPickerView(mContext);
//        }
//        areaPickerView.setPicker(provinceListMo.getProvince(), provinceListMo.getCityList(), true);
//        areaPickerView.setCyclic(false, false, false);
//        areaPickerView.setTitle(mContext.getString(R.string.dialog_area));
//        areaPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int option2, int options3) {
//                ProvinceMo provinceMo = provinceListMo.getProvince().get(options1);
//                AreaMo     cityMo     = provinceListMo.getCityList().get(options1).get(option2);
//                mbindBnakMo.setProvinceName(provinceMo.getPickerViewText());
//                mbindBnakMo.setCityName(cityMo.getPickerViewText());
//                mbindBnakMo.setProvince(provinceMo.getAreaCode());
//                mbindBnakMo.setCity(cityMo.getAreaCode());
//                String area = mbindBnakMo.getProvinceName() + mbindBnakMo.getCityName();
//                mBinding.bankarea.setText(area);
//                validator();
//            }
//        });
//    }

    /**
     * 银行帐号检测
     */
    public void bankCardNumCheck(String str) {
        str = EditTextFormat.getTrimStr(str);
        mbindBnakMo.setBankNo(str);
        validator();
    }

    /**
     * 确认添加
     */
    public void submit(View view) {
        res_addBank();
    }

    /**
     * 开户银行检测
     */
    public boolean banknameCheck() {
        if (TextUtils.isEmpty(mbindBnakMo.getBank())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 校验数据是否完整
     */
    public void validator() {

        if (!TextUtils.isEmpty(mbindBnakMo.getBankNo()) && banknameCheck()) {
            enable.set(true);
        } else {
            enable.set(false);
        }
        enable.notifyChange();
    }

//    /**
//     * 检验本地存储的area是否过期
//     */
//    private void res_version() {
//        Call<AreaVersionMo> call = RDClient.getService(ExtraService.class).arealistVersion();
//        call.enqueue(new RequestCallBack<AreaVersionMo>() {
//            @Override
//            public void onSuccess(Call<AreaVersionMo> call, Response<AreaVersionMo> response) {
//                AreaVersionMo localmo = SPUtil.getEntity(AreaVersionMo.class, null);
//                if (localmo == null || response.body().getVersion() > localmo.getVersion()) {
//                    res_area(response.body());
//                }
//            }
//        });
//    }

//    /**
//     * area过期，重新获取，并保存最新信息
//     *
//     * @param versionMo
//     */
//    private void res_area(final AreaVersionMo versionMo) {
//
//        Call<ProvinceListMo> call = RDClient.getService(ExtraService.class).area(UrlUtils.getAddress() + "/" + versionMo.getJson());
//        call.enqueue(new RequestCallBack<ProvinceListMo>() {
//            @Override
//            public void onSuccess(Call<ProvinceListMo> call, Response<ProvinceListMo> response) {
//                SPUtil.setEntity(response.body());
//                SPUtil.setEntity(versionMo);
//                setAreaData(response.body());
//            }
//        });
//    }

    /**
     * 绑卡初始化，获取开户行信息
     */
    private void res_toaddBank() {
        Call<InitialAddBankMo> call = RDClient.getService(BankService.class).toAddBank();
        NetworkUtil.showCutscenes(call);
        call.enqueue(new InitRequestCallBack<InitialAddBankMo>() {
            @Override
            public void onSuccess(Call<InitialAddBankMo> call, Response<InitialAddBankMo> response) {
                initialAddBank.set(response.body());
                opendingPickerView.setPicker(initialAddBank.get().getBankList());
                opendingPickerView.setCyclic(false);
            }
        });
    }

    /**
     * 绑卡
     */
    private void res_addBank() {
        String result = CheckBankNumber.luhmCheck(mbindBnakMo.getBankNo());
        if (!result.equals(Constant.TRUE)) {
            Utils.toast(result);
            return;
        }

        try {
            RefObject               refobj = RefObject.wrap(mbindBnakMo);
            HashMap<String, Object> param  = refobj.getField();
            PayCallBack payCallBack = new PayCallBack() {
                @Override
                public void callBack(boolean isSuccess) {
                    res_toaddBank();
                }
            };
            RDPayment.getInstance().getPayController().doPayment(null, PayController.TPYE_PAGEBINDCARD,param,payCallBack);
        } catch (RefException e) {
            e.printStackTrace();
        }
    }
}