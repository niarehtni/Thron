package com.rd.zhongqipiaoetong.view.entity;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.view.View;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.Utils;
import com.rd.zhongqipiaoetong.view.PageLoadingView;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/13 17:38
 * <p/>
 * Description: 空态对象({@link PageLoadingView})
 */
public class EmptyState extends BaseObservable {
    /**
     * 是否加载中
     */
    private boolean loading = true;
    /**
     * 空态图的 resId；
     */
    private Drawable         image;
    /**
     * 提示文字
     */
    private String           prompt;
    /**
     * 点击事件
     */
    private PageLoadingClick click;

    public EmptyState() {
    }

    @Bindable
    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(BR.loading);
    }

    @Bindable
    public Drawable getImage() {
        return image;
    }

    private void setImage(Drawable image) {
        this.image = image;
        notifyPropertyChanged(BR.image);
    }

    @Bindable
    public String getPrompt() {
        return prompt;
    }

    private void setPrompt(String prompt) {
        this.prompt = prompt;
        notifyPropertyChanged(BR.prompt);
    }

    /**
     * @param resId
     *         提示内容
     */
    public void setPrompt(@StringRes int resId) {
        setPrompt(resId, null);
    }

    /**
     * @param resId
     *         提示内容
     * @param click
     *         点击事件响应
     */
    public void setPrompt(@StringRes int resId, PageLoadingClick click) {
        Context context = ActivityUtils.peek();
        if (null != context && 0 != resId) {
            // 空态图resId
            int imageId = setImageByPrompt(resId);
            // resId转Drawable
            Drawable drawable = new BitmapDrawable(context.getResources(), Utils.readBitmap(context, imageId));
            // 设置遮罩颜色
//            drawable.setColorFilter(context.getResources().getColor(R.color.app_color_select), PorterDuff.Mode.MULTIPLY);

            setLoading(true);
            setImage(drawable);
            setPrompt(context.getString(resId));
        }
        if (null != click) {
            this.click = click;
        }
    }

    /**
     * 根据提示内容，显示空态图
     *
     * @param resId
     *         提示内容
     *
     * @return 空态图 resId
     */
    private int setImageByPrompt(int resId) {
        int imageId;
        switch (resId) {
            case R.string.empty_loading_error:
                // 缺省页_刷新
                imageId = R.drawable.empty_state_refresh;
                break;

            case R.string.empty_product:
                // 缺省页_无产品
                imageId = R.drawable.empty_state_product;
                break;

            case R.string.empty_network:
                // 缺省页_无网络
                imageId = R.drawable.empty_state_network;
                break;

            case R.string.empty_message:
                // 缺省页_无消息
                imageId = R.drawable.empty_state_message;
                break;

            case R.string.empty_bank_card:
                // 缺省页_银行卡
                imageId = R.drawable.empty_state_bank_card;
                break;

            case R.string.empty_redenvelope:
            case R.string.empty_experience:
            case R.string.empty_uprate:
            case R.string.empty_coupon:
                // 缺省页_优惠券
                imageId = R.drawable.empty_state_coupon;
                break;

            case R.string.empty_repaymentlog:
            case R.string.empty_invest:
            case R.string.empty_invest_bond:
            case R.string.empty_repayment:
            case R.string.empty_transaction:
            case R.string.empty_recharge:
            case R.string.empty_withdraw:
            case R.string.empty_record:
                // 缺省页_暂无记录
                imageId = R.drawable.empty_state_record;
                break;

            case R.string.empty_notice:
            case R.string.empty_activity:
            case R.string.empty_stay_tuned:
            default:
                // 缺省页_页面不存在
                imageId = R.drawable.empty_state_not_exist;
                break;
        }
        return imageId;
    }

    /**
     * PageLoadingView 点击事件
     */
    public void onClick(View view) {
        if (null != click) {
            click.onClick(view);
        }
    }

    /**
     * PageLoadingView 点击事件接口
     */
    public interface PageLoadingClick {
        void onClick(View view);
    }

    @BindingAdapter("emptyState")
    public static void emptyState(View view, EmptyState state) {
        ((PageLoadingView) view).setEmptyState(state);
    }
}