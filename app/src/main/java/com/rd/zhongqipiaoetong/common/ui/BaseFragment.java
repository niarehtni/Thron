package com.rd.zhongqipiaoetong.common.ui;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.rd.zhongqipiaoetong.utils.PermissionCheck;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/25 15:25
 * <p/>
 * Description: Fragment基类
 */
public class BaseFragment extends Fragment {
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionCheck.getInstance().onRequestPermissionsResult(getActivity(), requestCode, permissions, grantResults);
    }
}