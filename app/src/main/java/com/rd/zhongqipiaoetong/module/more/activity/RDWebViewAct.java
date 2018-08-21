package com.rd.zhongqipiaoetong.module.more.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.WebviewActBinding;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.Utils;
import com.rd.zhongqipiaoetong.view.KeyboardPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 6/1/16.
 */
public class RDWebViewAct extends BaseActivity {
    WebviewActBinding binding;
    private final static String DIALOG_TITLE = "提示";
    /**
     * webView  title 必传
     */
    String  title;
    /**
     * webView url 跳转  必传
     */
    String  url;
    /**
     * webView param拼接  非必传
     */
    String  params;
    /**
     * webView postData post数据  非必传
     */
    String  postData;
    /**
     * html内容  非必传
     */
    String  data;
    boolean needGoBack;
    /**
     * 调用相机
     */
    private ValueCallback<Uri>   mUploadMessage;// 表单的数据信息
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.webview_act);
        if (getIntent().getBooleanExtra(BundleKeys.ISCLOSE, false)) {
            binding.titleBar.appbar.setRightTextOption(binding.getRoot().getContext().getString(R.string.close), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        if (getIntent().getIntExtra(BundleKeys.LOADPROTOCOL,-1) == 1){//右侧按钮下载协议
            binding.titleBar.appbar.setRightTextOption("下载", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        title = getIntent().getStringExtra(BundleKeys.TITLE);
        url = getIntent().getStringExtra(BundleKeys.URL);
        params = getIntent().getStringExtra(BundleKeys.PARAMS);
        needGoBack = getIntent().getBooleanExtra(BundleKeys.NEED_GOBACK, false);
        postData = getIntent().getStringExtra(BundleKeys.POSTDATA);
        data = getIntent().getStringExtra(BundleKeys.DATA);

        if (params != null) {
            url = url + params;
        }
        System.out.println("url" + url);
        binding.rdWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     final JsResult result) {
                // 构建一个Builder来显示网页中的alert对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(RDWebViewAct.this);
                builder.setTitle(DIALOG_TITLE);
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok,
                        new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        });
                builder.setCancelable(false);
                builder.create();
                builder.show();
                return true;
            }

            /**
             * 调用相机
             */
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                take();
                return true;
            }

            //<3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                take();
            }
            /**
             * 调用相机
             */
            //>3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                take();
            }
            /**
             * 调用相机
             */
            //>4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                take();
            }
        });
        binding.rdWebview.addJavascriptInterface(new KeyboardPlugin(this,binding.rdWebview),"KeyboardJs");

        if (postData == null) {
            if (TextUtils.isEmpty(url)) {
                binding.rdWebview.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
            } else {
                binding.rdWebview.loadUrl(url);
            }
        } else {
            binding.rdWebview.postUrl(url, Utils.getBytes(postData.replace("?", ""), "BASE64"));
        }
    }

    @Override
    public void onBackPressed() {
        if (needGoBack) {
            if (binding.rdWebview.canGoBack()) {
                binding.rdWebview.goBack();
            } else {
                closeAndSetMsg();
                //                super.onBackPressed();
            }
        } else {
            closeAndSetMsg();
            //            super.onBackPressed();
        }
    }

    private void closeAndSetMsg() {
        setResult(999);
        ActivityUtils.pop(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(title);
    }

    /**
     * 调用相机
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL)
                return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                if (result != null) {
                    result = Uri.fromFile(null);
                    mUploadMessage.onReceiveValue(result);
                    mUploadMessage = null;
                } else {
                    mUploadMessage.onReceiveValue(result);
                    mUploadMessage = null;
                }
            }
        }
    }

    /**
     * 调用相机
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE || mUploadCallbackAboveL == null) {
            return;
        }

        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                results = new Uri[]{imageUri};
            } else {
                String   dataString = data.getDataString();
                ClipData clipData   = data.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        if (results != null) {
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        } else {
            results = new Uri[]{};
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        }
        return;
    }

    /**
     * 调用相机
     */
    private void take() {
        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
        if (!imageStorageDir.exists()) {
            imageStorageDir.mkdirs();
        }
        File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        imageUri = Uri.fromFile(file);
        final List<Intent>      cameraIntents  = new ArrayList<Intent>();
        final Intent            captureIntent  = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager    packageManager = getPackageManager();
        final List<ResolveInfo> listCam        = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent i           = new Intent(captureIntent);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            i.setPackage(packageName);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraIntents.add(i);
        }
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
    }
}
