package com.rd.zhongqipiaoetong.common;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.module.product.model.FinancingDetailMo;
import com.rd.zhongqipiaoetong.utils.Countdown;
import com.rd.zhongqipiaoetong.utils.DensityUtils;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;
import com.rd.zhongqipiaoetong.utils.EditTextFormat;
import com.rd.zhongqipiaoetong.view.DividerLine;
import com.rd.zhongqipiaoetong.view.listener.PtrFrameListener;

import java.util.LinkedList;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler2;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/23 14:55
 * Description: XML binding数据
 * <p/>
 * xmlns:app="http://schemas.android.com/apk/res-auto"
 */
public class XMLBinding {
    /**
     * 设置方向指示器
     */
    @BindingAdapter({"imageIndicator"})
    public static void imageIndicator(ImageView imageView, boolean isShow) {
        if (isShow) {
            imageView.setImageResource(R.drawable.ic_arrow_bottom);
        } else {
            imageView.setImageResource(R.drawable.ic_arrow_right);
        }
    }

    /**
     * 为ImageView设置图片
     */
    @BindingAdapter(value = {"src", "defaultImage", "isSkipCache"}, requireAll = false)
    public static void setImage(final ImageView imageView, String path, Drawable defaultImage, boolean isSkipCache) {
        if (null == defaultImage) {
            defaultImage = ContextCompat.getDrawable(imageView.getContext(), R.drawable.default_picture);
        }
        if (isSkipCache) {//是否缓存
            Glide.with(imageView.getContext()).load(path).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(defaultImage).into(imageView);
        } else {
            Glide.with(imageView.getContext()).load(path).error(defaultImage).into(imageView);
        }
    }

    /**
     * 设置view是否显示
     *
     * @param visible true - 显示
     *                false - 不显示
     */
    @BindingAdapter({"visibility"})
    public static void viewVisibility(View view, boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 设置显示内容值类型转换为string
     */
    @BindingAdapter({"toString"})
    public static void valueToString(TextView view, Object object) {
        view.setText(String.valueOf(object));
    }

    /**
     * 如果text为空，则不显示
     */
    @BindingAdapter({"text"})
    public static void setText(TextView view, String str) {
        if (TextUtils.isEmpty(str)) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            view.setText(str);
        }
    }

    /**
     * 为RecyclerView添加分割线
     *
     * @param type 水平 - HORIZONTAL = 0;
     *             垂直 - VERTICAL = 1;
     */
    @BindingAdapter({"addItemDecoration"})
    public static void addItemDecoration(RecyclerView view, int type) {
        DividerLine dividerLine;
        switch (type) {
            case DividerLine.HORIZONTAL:
                dividerLine = new DividerLine(DividerLine.HORIZONTAL);
                dividerLine.setMarginLeft(20);
                view.addItemDecoration(dividerLine);
                break;

            case DividerLine.VERTICAL:
                dividerLine = new DividerLine(DividerLine.VERTICAL);
                dividerLine.setMarginLeft(20);
                view.addItemDecoration(dividerLine);
                break;
            case DividerLine.CROSS:
                dividerLine = new DividerLine(DividerLine.CROSS);
                view.addItemDecoration(dividerLine);
                break;

            case DividerLine.DEFAULT:
            default:
                break;
        }
    }

    /**
     * 下拉刷新，上拉加载
     *
     * @param ptrFrame view
     * @param listener 实现操作
     */
    @BindingAdapter({"ptrFrame"})
    public static void ptrFrame(final PtrClassicFrameLayout ptrFrame, final PtrFrameListener listener) {
        if (null == listener) {
            ptrFrame.setEnabled(false);
            return;
        } else {
            ptrFrame.setEnabled(true);
        }
        ptrFrame.setPtrHandler(new PtrHandler2() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                listener.ptrFrameRefresh();
                listener.ptrFrameRequest(frame);
            }

            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler2.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onLoadBegin(PtrFrameLayout frame) {
                listener.ptrFrameRequest(frame);
            }

            public boolean checkCanDoLoad(PtrFrameLayout frame, View content, View footer) {
                return PtrDefaultHandler2.checkContentCanBePulledUp(frame, content, footer);
            }
        });
        ptrFrame.disableWhenHorizontalMove(true);
        // 初始化view
        ptrFrame.setLastUpdateTimeRelateObject(ptrFrame);
        ptrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
        listener.ptrFrameInit(ptrFrame);
    }

    /**
     * 设置TextView显示不同颜色的字体（仅限改变数字颜色）
     */
    @BindingAdapter(value = {"middleColor", "middleValue"}, requireAll = false)
    public static void middleColorShow(TextView view, Integer color, String value) {
        if (color == null) {
            throw new IllegalArgumentException("TextView color must not be null");
        }
        if (value == null) {
            //设置默认值
            value = "0";
            throw new IllegalArgumentException("TextView value must not be null");
        }
        view.setText(DisplayFormat.XLIFFNumFormat(value, color));
    }

    /**
     * 限制EditText只能输入两位小数
     *
     * @param toggle 开关
     */
    @BindingAdapter({"filter"})
    public static void lengthfilter(EditText view, boolean toggle) {
        if (toggle) {
            InputFilter[] old = view.getFilters();
            InputFilter[] filters = new InputFilter[old.length + 1];
            int position = 0;
            for (; position < old.length; position++) {
                filters[position] = old[position];
            }
            filters[position] = EditTextFormat.getLengthFilter();
            view.setFilters(filters);
        }
    }

    /**
     * 根据不同的状态，设置marginLeft的值
     *
     * @param type 开关
     */
    @BindingAdapter({"marginLeft"})
    public static void marginLeft(View view, int type) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
        if (type == 0) {
            lp.leftMargin = (int) view.getResources().getDimension(R.dimen.x0);
        } else {
            lp.leftMargin = (int) view.getResources().getDimension(R.dimen.x20);
        }
        view.setLayoutParams(lp);
    }

    /**
     * 投标按钮文字显示(对staus值进行转换)
     * <p/>
     * tip:标可投条件是 标的status是1并且标的余额不为0
     */
    @BindingAdapter(value = {"status", "timeToStart", "scales", "financingDetailMo"}, requireAll = false)
    public static void statusType(TextView view, String status, long timeToStart, String scales, FinancingDetailMo fMo) {
        if (!(status == null || status.equals(""))) {
            Context context = view.getContext();
            view.setEnabled(false);
            if (status.equals("1")) {
                if (fMo.getBorrowVO().isOwn()) {
                    view.setText("借款人不可投资");
                    return;
                }
                if (1 == fMo.getBorrowVO().getCategory()) {
                    if (fMo.getBorrowVO().isInvestAble()) {
                        view.setText("立即投资");
                        view.setEnabled(true);
                    } else {
                        view.setText("仅限新手");
                        return;
                    }
                }
                if (Double.valueOf(scales) == 100) {
                    view.setText("满标待审");
                } else {
                    view.setText("立即投资");
                    view.setEnabled(true);
                }
            } else if (status.equals("3")) {
                view.setText("招标结束");
            } else if (status.equals("4")) {
                view.setText("还款中");
            } else if (status.equals("8")) {
                view.setText("已还款");
            } else {
                view.setText("招标结束");
            }

            //保持文字居中,不设此属性显示会有问题
            view.setGravity(Gravity.CENTER);
            //定时判断
            timeToStart = timeToStart == 0 ? -1 : timeToStart;
            Long StartTime = Long.valueOf(timeToStart);

            if (StartTime > 0) {
                new Countdown(view.getContext(), view, StartTime, "剩余", true);
            }
        } else {
            view.setEnabled(false);
        }
    }

    /**
     * 剩余金额不足时,不与输入投资金额
     */
    @BindingAdapter(value = {"minMoney", "remainMoney"}, requireAll = true)
    public static void canEdit(EditText ed, double minMoney, double remainMoney) {
        if (minMoney > remainMoney) {
            ed.setEnabled(false);
            ed.setText(String.valueOf(remainMoney));
        }
    }

    /**
     * 设置Textview drawable ColorFilter
     *
     * @param tv
     * @param drawableLeft
     * @param drawableTop
     * @param drawableRight
     * @param drawableBottom
     * @param color
     */
    @BindingAdapter(value = {"drawableLeft", "drawableTop", "drawableRight", "drawableBottom", "colorFilter"}, requireAll = false)
    public static void drawableText(TextView tv, Drawable drawableLeft, Drawable drawableTop, Drawable drawableRight, Drawable drawableBottom, int color) {
        if (0 == color) {
            color = ContextCompat.getColor(tv.getContext(), R.color.app_color_principal);
        }
        if (null != drawableLeft) {
            drawableLeft.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        }
        if (null != drawableTop) {
            drawableTop.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        }
        if (null != drawableBottom) {
            drawableRight.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        }
        if (null != drawableBottom) {
            drawableRight.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        }
        tv.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
    }

    /**
     * 设置Textview drawable ColorFilter
     *
     * @param imageView
     * @param color
     */
    @BindingAdapter(value = {"drawable", "colorFilter"}, requireAll = true)
    public static void drawableImageView(ImageView imageView, Drawable drawable, int color) {
        if (0 == color) {
            color = ContextCompat.getColor(imageView.getContext(), R.color.app_color_principal);
        }
        if (null != drawable) {
            drawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        }

        imageView.setImageDrawable(drawable);
    }

    /**
     * list 中 EditText 变化通知
     */
    @BindingAdapter(value = {"watcher", "list"}, requireAll = false)
    public static void setEditChangelistener(EditText ed, EditTextFormat.EditTextFormatWatcher watcher, LinkedList<EditText> edlist) {
        if (null != watcher) {
            EditTextFormat.editChange(ed, watcher);
        }
        if (null != edlist) {
            edlist.add(ed);
        }
    }

    /**
     * 添加背景透明度,不加mutate方法改变一处即会改变全局背景的alpha值（5.0以上时会出现此问题）
     */
    @BindingAdapter({"alpha"})
    public static void setalpha(View view, int alpha) {
        view.getBackground().mutate().setAlpha(alpha);
    }

    @BindingAdapter({"statusStr"})
    public static void setStatusString(TextView textView, String status) {
        if ("0".equals(status)) {
            textView.setText("投资中");
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.text_grey));
        } else if ("1".equals(status)) {
            textView.setText("成功");
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.text_grey));
        } else if ("2".equals(status)) {
            textView.setText("失败");
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.text_red));
        } else if ("3".equals(status)) {
            textView.setText("还款完成");
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.text_grey));
        }
    }

    @BindingAdapter({"homeIconType"})
    public static void setHomeIconType(TextView textView, String homeIconType) {
        Drawable drawable;
        int size = textView.getContext().getResources().getDimensionPixelSize(R.dimen.x45);
        if ("101".equals(homeIconType)) {
            drawable = textView.getContext().getResources().getDrawable(R.drawable.invest_dan);
            drawable.setBounds(0, 0, size, size);
        } else if ("102".equals(homeIconType)) {
            drawable = textView.getContext().getResources().getDrawable(R.drawable.invest_ya);
            drawable.setBounds(0, 0, size, size);
        } else if ("103".equals(homeIconType)) {
            drawable = textView.getContext().getResources().getDrawable(R.drawable.invest_xin);
            drawable.setBounds(0, 0, size, size);
        } else {
            drawable = null;
        }
        textView.setCompoundDrawables(null, null, drawable, null);
    }

    @BindingAdapter({"imageType"})
    public static void setImageType(ImageView imageView, String imageType) {
        if ("101".equals(imageType)) {
            imageView.setImageResource(R.drawable.invest_dan);
            imageView.setVisibility(View.VISIBLE);
        } else if ("102".equals(imageType)) {
            imageView.setImageResource(R.drawable.invest_ya);
            imageView.setVisibility(View.VISIBLE);
        } else if ("103".equals(imageType)) {
            imageView.setImageResource(R.drawable.invest_xin);
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }

    @BindingAdapter(value = {"feeType", "fee"})
    public static void setFee(TextView textView, String feeType, double fee) {
        if (feeType == null || "".equals(feeType))
            return;
        if (feeType.equals("1")) {
            textView.setText(String.format(textView.getContext().getResources().getString(R.string.transfer_rule_tips1), DisplayFormat.doubleFormat(fee)));
        } else if (feeType.equals("2")) {
            textView.setText(String.format(textView.getContext().getResources().getString(R.string.transfer_rule_tips2), DisplayFormat.doubleFormat(fee)));
        }
    }

    /**
     * 设置view的宽高比
     *
     * @param view        view对象
     * @param widthRatio  宽度占比
     * @param aspectRatio 宽高比
     */
    @BindingAdapter(value = {"widthRatio", "aspectRatio"}, requireAll = false)
    public static void aspectRatio(View view, float widthRatio, float aspectRatio) {
        // 宽度
        float width;
        // 高度
        float height;
        // view 的 LayoutParams
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        if (widthRatio != 0) {
            if (widthRatio > 1) {
                widthRatio = 1;
            }
            width = DensityUtils.getWidth((Activity) view.getRootView().getContext()) * widthRatio;
        } else {
            width = DensityUtils.getWidth((Activity) view.getRootView().getContext());
        }

        if (aspectRatio != 0) {
            height = width * aspectRatio;
        } else {
            height = layoutParams.height;
        }

        layoutParams.width = (int) width;
        layoutParams.height = (int) height;

        view.setLayoutParams(layoutParams);
        view.requestLayout();
    }
}