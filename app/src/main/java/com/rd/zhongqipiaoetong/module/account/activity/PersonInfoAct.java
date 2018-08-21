package com.rd.zhongqipiaoetong.module.account.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.View.OnClickListener;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.rd.zhongqipiaoetong.BuildConfig;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BaseParams;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.info.SharedInfo;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.AccountPersonInfoActBinding;
import com.rd.zhongqipiaoetong.module.account.model.PersonInfoMo;
import com.rd.zhongqipiaoetong.module.account.model.TokenMo;
import com.rd.zhongqipiaoetong.module.account.viewmodel.PersonInfoVM;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.AccountService;
import com.rd.zhongqipiaoetong.network.entity.HttpResult;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;
import com.rd.zhongqipiaoetong.utils.Utils;

import org.json.JSONObject;

import java.io.File;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/24 9:34
 * <p/>
 * Description: 账户与安全(个人信息)
 */
public class PersonInfoAct extends BaseActivity {
    private static final String IMAGE_FILE_NAME = "avatarImage.jpg";
    private static final int REQUEST_CODE_PICK = 0;        // 从相册中选择
    private static final int REQUEST_CODE_TAKE = 1;        // 拍照
    private static final int REQUEST_CODE_CUTTING = 2;        // 结果
    //
    private AccountPersonInfoActBinding binding;
    private PersonInfoVM infoVM;
    private OnClickListener itemsOnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.account_person_info_act);
        init();
        infoVM = new PersonInfoVM(itemsOnClick);
        binding.setViewModel(infoVM);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.person_info_title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        req_data();
    }

    /**
     * 网络请求
     */
    private void req_data() {
        Call<PersonInfoMo> call = RDClient.getService(AccountService.class).securityInfo();
        call.enqueue(new RequestCallBack<PersonInfoMo>() {
            @Override
            public void onSuccess(Call<PersonInfoMo> call, Response<PersonInfoMo> response) {
                infoVM.info.set(response.body());
                infoVM.info.get().setPhone(DisplayFormat.getSecurityName(infoVM.info.get().getPhone()));
                infoVM.info.get().setUsername(DisplayFormat.getSecurityName(infoVM.info.get().getUsername()));
                if (response.body().getRealNameStatus() == 1 || response.body().getRealNameStatus() == 3) {
                    infoVM.info.get().setInfoStatus(response.body().getRealName());
                } else if (response.body().getRealNameStatus() == 6) {
                    infoVM.info.get().setInfoStatus("已认证");
                } else {
                    infoVM.info.get().setInfoStatus(getResources().getString(R.string.person_info_go_open));
                }
                SharedInfo.getInstance().setValue(PersonInfoMo.class, response.body());
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        itemsOnClick = new OnClickListener() {
            @Override
            public void onClick(View v) {
                infoVM.popupWindow.dismiss();
                switch (v.getId()) {
                    // 拍照
                    case R.id.takePhotoBtn:
                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, getFileUri(new File(BaseParams.ROOT_PATH, IMAGE_FILE_NAME)));
                            startActivityForResult(intent, REQUEST_CODE_TAKE);
                        }
                        break;

                    // 从相册中选择
                    case R.id.pickPhotoBtn: {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, REQUEST_CODE_PICK);
                    }
                    break;

                    default:
                        break;
                }
            }
        };
    }

    private Uri getFileUri(File file) {
        if (Build.VERSION.SDK_INT >= 24) {
            return FileProvider.getUriForFile(PersonInfoAct.this, BuildConfig.APPLICATION_ID.split("\\.dealing")[0] + ".provider", file);
        } else {
            return Uri.fromFile(file);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 从本地获取图片
                case REQUEST_CODE_PICK:
                    // 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
                    if (data != null) {
                        startPhotoZoom(data.getData());
                    }
                    break;

                // 拍照
                case REQUEST_CODE_TAKE:
                    File file = new File(BaseParams.ROOT_PATH + "/" + IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(file));
                    break;

                // 剪裁
                case REQUEST_CODE_CUTTING:
                    try {
//                        Uri uritempFile = Uri.parse("file://" + BaseParams.ROOT_PATH + "/" + IMAGE_FILE_NAME);
//                        Bitmap cropBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
                        Bitmap cropBitmap = data.getExtras().getParcelable("data");
                        Utils.saveFile(PersonInfoAct.this, BaseParams.ROOT_PATH, IMAGE_FILE_NAME, cropBitmap);
                        req_uploadImg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    /**
     * 剪裁头像
     */
    public void startPhotoZoom(Uri uri) {
//        File CropPhoto = new File(BaseParams.ROOT_PATH + "/" + IMAGE_FILE_NAME);
//        try {
//            if (CropPhoto.exists()) {
//                CropPhoto.delete();
//            }
//            CropPhoto.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        if (Build.VERSION.SDK_INT >= 24) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        // aspectX aspectY
        if (android.os.Build.BRAND.contains("HUAWEI") || Build.BRAND.contains("HONOR")) {
            intent.putExtra("aspectX", 9998);
            intent.putExtra("aspectY", 9999);
        } else {
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
        }
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
//        Uri uritempFile = Uri.fromFile(CropPhoto);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("noFaceDetection", true);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, REQUEST_CODE_CUTTING);
    }

    /**
     * 上传头像
     */
    private void req_uploadImg() {
        Call<TokenMo> call = RDClient.getInstance().getService(AccountService.class).qiuniuToken();
        call.enqueue(new RequestCallBack<TokenMo>() {
            @Override
            public void onSuccess(Call<TokenMo> call, Response<TokenMo> response) {
                UploadManager uploadManager = new UploadManager();
                File data = new File(BaseParams.ROOT_PATH + "/" + IMAGE_FILE_NAME);
                uploadManager.put(data, null, response.body().getToken(), new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if (info.isOK()) {
                            Call<HttpResult> call = RDClient.getInstance().getService(AccountService.class).upload(response.optString("key"));
                            call.enqueue(new RequestCallBack<HttpResult>() {
                                @Override
                                public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                                    req_data();
                                }
                            });
                        } else {

                        }
                    }
                }, null);
            }
        });

        //        String                   path        = BaseParams.ROOT_PATH + "/" + IMAGE_FILE_NAME;
        //        File                     file        = new File(path);
        //        RequestBody              requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        //        RequestBody              type        = RequestBody.create(MediaType.parse("text/plain"), "avatar");
        //        Map<String, RequestBody> map         = new HashMap<>();
        //        map.put("headPortraitUrl\"; filename=\"image.png\"", requestBody);
        //        map.put("nid", type);
        //        Call<HttpResult> call = RDClient.getService(AccountService.class).toImage(UrlUtils.getUrl2Image(), map);
        //        call.enqueue(new RequestCallBack<HttpResult>() {
        //            @Override
        //            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
        //                String imgurl = response.body().getRes_data().toString();
        //                imgurl = imgurl.substring(1, imgurl.length() - 1);
        //                String[] url = imgurl.split("=");
        //                req_imgurl(url[1]);
        //            }
        //        });
    }

    //    private void requestToImage(String path) {
    //        final File data = new File(path);
    //        Call<HttpResult> call = RDClient.getService(AccountService.class).toImage(UrlUtils.getUrl3Image(), new SUploadAvatarBean());
    //        call.enqueue(new RequestCallBack<HttpResult>() {
    //
    //                    @Override
    //                    public void onData(Call<HttpResult> call, Response<HttpResult> response){
    //                        String token = response.body().
    //                        uploadManager = new UploadManager();
    //                        final String expectKey = null;
    //                        final String token = data1.getString("token");
    //                        getActivity().runOnUiThread(new Runnable() {
    //                            @Override
    //                            public void run() {
    //                                // TODO Auto-generated method stub
    //                                uploadManager.put(data, expectKey,token, new UpCompletionHandler() {
    //                                    public void complete(String k, ResponseInfo rinfo, JSONObject response) {
    //                                        Log.i("qiniutest", k + rinfo + response.toString());
    //                                        SUploadAvatarBean sBean = new SUploadAvatarBean();
    //                                        sBean.setHeadPortraitUrl(response.optString("key"));
    //                                        NetUtils.send(AppUtils.API_UPLOADHEAD, sBean, new EasyRequset() {
    //
    //                                            @Override
    //                                            protected void onData(JSONObject data) throws JSONException {
    //                                                // TODO Auto-generated method stub
    //                                                AppTools.toast(getString(R.string.account_safe_tv_upload_success));
    //                                                initRealInfo();
    //                                            }
    //                                        });
    //                                    }
    //                                }, null);
    //                            }
    //                        });
    //
    //                    }
    //
    //                }, true);
    //    }

    private void req_imgurl(String imgurl) {
        Call<HttpResult> call = RDClient.getService(AccountService.class).upload(imgurl);
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                req_data();
            }
        });
    }

    @Override
    public void finish() {
        setResult(BundleKeys.RESULT_CODE_INFO);
        super.finish();
    }
}