package com.rd.zhongqipiaoetong.module.product.viewmodel;

import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableDouble;
import android.view.View;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.account.model.CouponMo;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Description:选择红包的vm
 */
public class ChoiceCouponVM extends BaseRecyclerViewVM<CouponMo> {
    //    private ArrayList<CouponMo> couponMoList;
    private      ObservableArrayList<CouponMo> couponMolist = new ObservableArrayList<>();
    //红包总额
    public final ObservableDouble              redAllMoney  = new ObservableDouble();
    //所选择的红包的总金额
    public final ObservableDouble              redNum       = new ObservableDouble();
    //以投金额
    private String tenderMoney;
    //可使用红包的比例
    private double tenderRedEnvelopeRate;
    //可以使用的红包上限(向下取整)
    private double redMax;
    //存储所选红包的id(key)和金额(value)的map
    private HashMap<String, String> redCollect = new HashMap<>();
    private double                  redCapital = 0;

    public ChoiceCouponVM(ArrayList<CouponMo> couponMoList, double tenderRedEnvelopeRate, String tenderMoney, String redString) {
        this.tenderRedEnvelopeRate = tenderRedEnvelopeRate;
        this.tenderMoney = tenderMoney;
        couponMolist.addAll(couponMoList);
        items.addAll(couponMolist);

        initMap(redString);
    }

    @Override
    protected void selectView(ItemView itemView, int position, CouponMo item) {
        itemView.set(BR.item, R.layout.account_my_coupon_item);
        itemView.setVariable(BR.isShow, true);

        //填充map数据
        itemView.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {

                checkANDput(couponMolist.get(i));
            }
        });
    }

    //确定按钮
    public void sureChoice(View view) {
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.REDSTRING, changeToString());
        intent.putExtra(BundleKeys.REDSELECTAMOUNT, redNum.get());
        ActivityUtils.pop(ActivityUtils.peek(), intent);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 数据填装和拆解
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 填充map
     * 初始化
     *
     * @param redString
     */
    private void initMap(String redString) {
        redMax = Math.floor(Double.valueOf(tenderMoney) * tenderRedEnvelopeRate);

        String[] red = redString.split(",");
        for (CouponMo mo : couponMolist) {
            //初始化时算出红包总额
            redAllMoney.set(redAllMoney.get() + Double.valueOf(mo.getAmount()));

            if (red.length != 0) {
                for (int i = 0; i < red.length; i++) {
                    String id = red[i];
                    if (mo.getId().equals(id)) {
                        redCollect.put(id, mo.getAmount());
                        redNum.set(redNum.get() + Double.valueOf(mo.getAmount()));
                        mo.setCheck(true);
                    }
                }
            }
        }
    }

    /**
     * 红包添加
     *
     * @param item
     */
    private void checkANDput(CouponMo item) {
        String redId = item.getId();

        if (item.isCheck()) {
            /*此处是删除选中的红包*/
            redCollect.remove(redId);
            item.setCheck(false);
            redNum.set(redNum.get() - Double.valueOf(item.getAmount()));
            redCapital = redCapital - Double.valueOf(item.getMinCapital());
        } else {
            /*此处是添加选中的红包*/
            redCapital += Double.valueOf(item.getMinCapital());
            double redNext = redNum.get() + Double.valueOf(item.getAmount());
            //判读是否超出可使用红包的上限
            if (redCapital > Double.valueOf(tenderMoney)) {
                Utils.toast("未达到使用红包的投资金额,需投资满" + redCapital + "元");
                redCapital -= Double.valueOf(item.getMinCapital());
                return;
            }
            item.setCheck(true);
            redNum.set(redNext);
            redCollect.put(redId, item.getAmount());
        }
    }

    /**
     * 将所选的红包转换成String格式如 {23,34,56}
     * 如没有数据,返会""
     *
     * @return
     */
    private String changeToString() {
        String redString = "";

        if (!redCollect.isEmpty()) {
            StringBuilder sb   = new StringBuilder();
            Iterator      iter = redCollect.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object    key   = entry.getKey();
                sb.append(key.toString()).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            redString = sb.toString();
        }
        return redString;
    }
}