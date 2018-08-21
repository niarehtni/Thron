package com.rd.zhongqipiaoetong.module.product.viewmodel;

import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableDouble;
import android.view.View;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.account.model.ExperienceMo;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Description:选择加息券的vm
 */
public class ChoiceExpVM extends BaseRecyclerViewVM<ExperienceMo> {
    private ObservableArrayList<ExperienceMo> experienceMoArrayList = new ObservableArrayList<>();
    //体验券可使用的最大金额
    private double maxExp;
    //可选体验券
    public  String expNum;
    //所选择的体验券的总金额
    public final ObservableDouble expAmount = new ObservableDouble();
    //以投金额
    private String tenderMoney;
    //存储所选体验券的id(key)和金额(value)的map
    private HashMap<String, String> expCollect = new HashMap<>();
    //是否为无限投体验标
    private boolean isInfinity;

    public ChoiceExpVM(ArrayList<ExperienceMo> expMoList, String expString, String tenderMoney, double maxExp, boolean isInfinity) {
        this.tenderMoney = tenderMoney;
        this.maxExp = maxExp;
        this.isInfinity = isInfinity;
        experienceMoArrayList.addAll(expMoList);
        items.addAll(experienceMoArrayList);
        expNum = String.valueOf(experienceMoArrayList.size());

        initMap(expString);
    }

    @Override
    protected void selectView(ItemView itemView, int position, final ExperienceMo item) {
        itemView.set(BR.item, R.layout.account_my_experience_item);
        itemView.setVariable(BR.isShow, true);

        //填充map数据
        itemView.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                ExperienceMo mo = experienceMoArrayList.get(i);
                checkANDput(mo);
            }
        });
    }

    //确定按钮
    public void sureChoice(View view) {
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.EXPSTRING, changeToString());
        intent.putExtra(BundleKeys.EXPAMOUNT, String.valueOf(expAmount.get()));
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
        //        redMax = Double.valueOf(tenderMoney) * tenderRedEnvelopeRate;

        String[] exp = redString.split(",");
        for (ExperienceMo mo : experienceMoArrayList) {
            //初始化时算出红包总额
            //            redAllMoney.set(redAllMoney.get() + Double.valueOf(mo.getAmount()));

            if (exp.length != 0) {
                for (int i = 0; i < exp.length; i++) {
                    String id = exp[i];
                    if (mo.getId().equals(id)) {
                        expCollect.put(id, mo.getAmount());
                        expAmount.set(expAmount.get() + Double.valueOf(mo.getAmount()));
                        mo.setIsCheck(true);
                    }
                }
            }
        }
    }

    /**
     * 体验券添加
     *
     * @param item
     */
    private void checkANDput(ExperienceMo item) {
        String redId = item.getId();

        if (item.getIsCheck()) {
            /*此处是删除选中的体验券*/
            expCollect.remove(redId);
            item.setIsCheck(false);
            expAmount.set(expAmount.get() - Double.valueOf(item.getAmount()));
        } else {
            /*此处是添加选中的体验券*/

            double redNext = expAmount.get() + Double.valueOf(item.getAmount());
            //判读是否超出可使用体验券的上限
            if (redNext > maxExp && !isInfinity) {
                Utils.toast("可使用体验券额度为" + maxExp);
                return;
            }
            item.setIsCheck(true);
            expAmount.set(redNext);
            expCollect.put(redId, item.getAmount());
        }
    }

    /**
     * 将所选的体验券转换成String格式如 {23,34,56}
     * 如没有数据,返会""
     *
     * @return
     */
    private String changeToString() {
        String expString = "";

        if (!expCollect.isEmpty()) {
            StringBuilder sb = new StringBuilder();


            Iterator iter = expCollect.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object    key   = entry.getKey();
                sb.append(key.toString()).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            expString = sb.toString();
        }
        return expString;
    }
}