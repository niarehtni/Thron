package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.Constant;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.account.activity.BankCardListAct;
import com.rd.zhongqipiaoetong.module.account.model.BankCardListMo;
import com.rd.zhongqipiaoetong.module.account.model.BankCardMo;
import com.rd.zhongqipiaoetong.module.account.model.TppConfineMo;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.BankService;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.UpDataListener;

import me.tatarka.bindingcollectionadapter.ItemView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/23 17:18
 * <p/>
 * Description: 银行卡列表VM({@link BankCardListAct})
 */
public class BankCardListVM extends BaseRecyclerViewVM<BankCardMo> {
    /**
     * 是否选择银行卡
     */
    private boolean                      iSelected;
    private UpDataListener<TppConfineMo> upDataListener;
    private String                       authorizeType;
    private BaseActivity                 activity;

    @Override
    protected void selectView(ItemView itemView, final int position, final BankCardMo item) {
        itemView.set(BR.info, R.layout.account_bank_card_list_item).setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                if (iSelected) {
                    Intent intent = new Intent();
                    intent.putExtra(BundleKeys.BANKCARDMO, items.get(i));
                    ActivityUtils.pop(ActivityUtils.peek(), intent);
                }
            }
        });
        item.setAuthorizeType(authorizeType);
    }

    public BankCardListVM(BaseActivity activity, boolean iSelected, UpDataListener<TppConfineMo> upDataListener) {
        this.activity = activity;
        this.iSelected = iSelected;
        this.upDataListener = upDataListener;
        hidden.set(false);
    }

    public void req_data() {
        Call<BankCardListMo> call = RDClient.getService(BankService.class).bankList();
        call.enqueue(new RequestCallBack<BankCardListMo>() {
            @Override
            public void onSuccess(Call<BankCardListMo> call, Response<BankCardListMo> response) {
                if (emptyState.isLoading()) {
                    emptyState.setLoading(false);
                }
                authorizeType = response.body().getAuthorizeType();
//                if ("".equals(response.body().getTppType())) {
//                    activity.setRightText(activity.getString(R.string.bc_add), new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            RDPayment.getInstance().getPayController().doPayment(null, PayController.TPYE_BINDCARD, null, null);
//                        }
//                    });
//                }
                items.clear();
                items.addAll(response.body().getBankCardList());
                upDataListener.updata(Constant.NUMBER_0, response.body().getTppConfine());
                if (response.body().getBankCardList().size() < 1) {
                    emptyState.setPrompt(R.string.empty_bank_card);
                }
            }
        });
    }

    /**
     * 根据银行卡类型，设置不同的背景色
     */
    @BindingAdapter({"bankCardBg"})
    public static void bankCardBg(View view, String type) {
        int[] colors;
        if (type.equals("ABC")) {
            colors = new int[]{0xFF1BAD8B, 0xFF0997B1};
        } else {
            colors = new int[]{0xFFE15962, 0xFFE14D79};
        }
        // 创建drawable
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
        // 内部填充颜色
        // int fillColor = Color.parseColor("#DFDFE0");
        // gd.setColor(fillColor);

        // 8dp 圆角半径;
        float[] roundRadii = {30f, 30f, 30f, 30f, 0f, 0f, 0f, 0f};
        gd.setCornerRadii(roundRadii);

        // int strokeWidth = 0; // 边框宽度
        // int strokeColor = Color.parseColor("#2E3135");//边框颜色
        // gd.setStroke(strokeWidth, strokeColor);

        // int     roundRadius = 15; // 8dp 圆角半径
        // gd.setCornerRadius(roundRadius);
        view.setBackgroundDrawable(gd);
        //        view.setBackgroundDrawable(setBg(gd));
    }

    /**
     * 设置item的点击颜色
     */
    private static StateListDrawable setBg(GradientDrawable gd) {
        // 初始化一个空对象
        StateListDrawable stalistDrawable = new StateListDrawable();

        // 获取对应的属性值 Android框架自带的属性 attr
        int pressed        = android.R.attr.state_pressed;
        int window_focused = android.R.attr.state_window_focused;
        int focused        = android.R.attr.state_focused;
        int selected       = android.R.attr.state_selected;

        // 创建drawable
        GradientDrawable bg = new GradientDrawable();
        // 8dp 圆角半径;
        float[] roundRadii = {30f, 30f, 30f, 30f, 0f, 0f, 0f, 0f};
        bg.setCornerRadii(roundRadii);
        bg.setColor(Color.parseColor("#A0D0D0D0"));

        stalistDrawable.addState(new int[]{pressed, window_focused}, bg);
        stalistDrawable.addState(new int[]{pressed, -focused}, bg);
        stalistDrawable.addState(new int[]{selected}, bg);
        stalistDrawable.addState(new int[]{focused}, bg);
        // 没有任何状态时显示的图片，我们给它设置为空集合
        stalistDrawable.addState(new int[]{}, gd);
        return stalistDrawable;
    }
}