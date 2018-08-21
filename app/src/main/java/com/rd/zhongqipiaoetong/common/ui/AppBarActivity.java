package com.rd.zhongqipiaoetong.common.ui;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.utils.Utils;
import com.rd.zhongqipiaoetong.view.AppBar;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/21 15:02
 * <p/>
 * Description: 带AppBar的activity
 */
public class AppBarActivity extends AppCompatActivity {
    // title bar
    protected AppBar mAppBar = null;

    @Override
    protected void onStart() {
        super.onStart();
        if (null == mAppBar) {
            mAppBar = (AppBar) findViewById(R.id.appbar);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    ///////////////////////////////////////////////////////////////////////////
    // title
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 为appbar设置标题
     */
    @Override
    public void setTitle(CharSequence title) {
        if (null != mAppBar && !TextUtils.isEmpty(title)) {
            mAppBar.setTitle(title);
        }
    }

    public void setTitle(CharSequence title, @ColorInt int color) {
        setTitle(title);
        setTitleColor(color);
    }

    /**
     * 为appbar设置标题
     */
    @Override
    public void setTitle(@StringRes int resId) {
        if (null != mAppBar && resId != 0) {
            mAppBar.setTitle(getString(resId));
        }
    }

    public void setTitle(@StringRes int resId, @ColorInt int color) {
        setTitle(resId);
        setTitleColor(color);
    }

    /**
     * 为appbar设置标题的颜色
     */
    @Override
    public void setTitleColor(@ColorInt int color) {
        if (null != mAppBar && color != 0) {
            mAppBar.setTitleColor(color);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // left
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 设置是否显示back键
     */
    public void setBackOption(boolean option) {
        if (null != mAppBar) {
            mAppBar.setBackOption(option);
        }
    }

    /**
     * 设置左侧文字
     */
    public void setLeftText(CharSequence text, View.OnClickListener listener, @ColorInt int color) {
        if (null != mAppBar) {
            mAppBar.setLeftTextOption(text, listener);
            if (color != 0) {
                mAppBar.setLeftTextColor(color);
            }
        }
    }

    public void setLeftText(CharSequence text, View.OnClickListener listener) {
        setLeftText(text, listener, 0);
    }

    /**
     * 设置左侧文字
     */
    public void setLeftText(int resId, View.OnClickListener listener, @ColorInt int color) {
        if (null != mAppBar) {
            mAppBar.setLeftTextOption(getString(resId), listener);
            if (color != 0) {
                mAppBar.setLeftTextColor(color);
            }
        }
    }

    public void setLeftText(int resId, View.OnClickListener listener) {
        setLeftText(resId, listener, 0);
    }

    /**
     * 设置左侧返回健的图片和监听事件
     * @param resId
     * @param listener
     */
    public void setLeftImage(@DrawableRes int resId, View.OnClickListener listener){
        mAppBar.setLeftIconOption(resId, listener);
    }

    ///////////////////////////////////////////////////////////////////////////
    // right
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 设置右侧文字
     */
    public void setRightText(CharSequence text, View.OnClickListener listener, @ColorInt int color) {
        if (null != mAppBar) {
            mAppBar.setRightTextOption(text, listener);
            if (color != 0) {
                mAppBar.setRightTextColor(color);
            }
        }
    }

    public void removeRightText() {
        if (null != mAppBar) {
            mAppBar.removeRightText();
        }
    }

    public void setRightText(CharSequence text, View.OnClickListener listener) {
        setRightText(text, listener, 0);
    }

    /**
     * 设置右侧文字
     */
    public void setRightText(int resId, View.OnClickListener listener, @ColorInt int color) {
        if (null != mAppBar) {
            mAppBar.setRightTextOption(getString(resId), listener);
            if (color != 0) {
                mAppBar.setRightTextColor(color);
            }
        }
    }

    public void setRightText(int resId, View.OnClickListener listener) {
        setRightText(resId, listener, 0);
    }

    public void setReightImage(int resId, View.OnClickListener listener) {
        if (null != mAppBar) {
            mAppBar.setRightIconOption(resId, listener);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // appbar
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 从view中获取color，用以设置appbar的颜色
     */
    public void setAppBarColor(View view) {
        if (null != mAppBar && null != view) {
            Drawable drawable = view.getBackground();
            if (drawable instanceof GradientDrawable) {
                return;
            } else if (drawable instanceof ColorDrawable) {
                setParentSelector(((ColorDrawable) drawable).getColor());
                return;
            }
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;

            Palette palette = Palette.from(bitmapDrawable.getBitmap()).generate();
            // getVibrantSwatch();      // 有活力
            // getDarkVibrantSwatch();  // 有活力 暗色
            // getLightVibrantSwatch(); // 有活力 亮色
            // getMutedSwatch();        // 柔和
            // getDarkMutedSwatch();    // 柔和 暗色
            // getLightMutedSwatch();   // 柔和 亮色
            Palette.Swatch vibrant = palette.getLightVibrantSwatch();
            if (null != vibrant) {
                // getRgb(): the RGB value of this color.
                // getBodyTextColor(): the RGB value of a text color which can be displayed on top of this color.
                // getTitleTextColor(): the RGB value of a text color which can be displayed on top of this color.
                // getPopulation(): the amount of pixels which this swatch represents.
                // getHsl(): the HSL value of this color.
                setParentSelector(vibrant.getRgb());
            }
        }
    }

    /**
     * 设置appbar的颜色
     */
    public void setAppBarColor(@ColorInt int color) {
        setParentSelector(color);
    }

    /**
     * 为LeftImage设置Selector
     */
    private void setParentSelector(@ColorInt int color) {
        int colorBurn = Utils.colorBurn(color);
        if (null != mAppBar && color != 0) {
            mAppBar.setBackgroundColor(color);
            mAppBar.setLeftParentSelector(color, colorBurn);
        }

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.setStatusBarColor(colorBurn);
            window.setNavigationBarColor(colorBurn);
        }
    }
}